package com.tony.manager;

import com.tony.constants.EnumResponseState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangwenjie 2019/10/22
 */
@Slf4j
public class SocketManager {
    private ChannelGroup channels;

    private static class SingletonHolder {
        static final SocketManager INSTANCE = new SocketManager();
    }

    private SocketManager() {
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    public static SocketManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Channel getChannel(String addressKey) {
        return channels.stream()
                .filter(channel -> addressKey.equals(channel.remoteAddress().toString()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("channel [" + addressKey + "] is not online")
                );
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
    }

    public EnumResponseState send(RpcCmd rpcCmd) {
        return send(rpcCmd.getRemoteAddressKey(), rpcCmd);
    }

    public EnumResponseState send(String addressKey, RpcCmd rpcCmd) {
        Channel channel = getChannel(addressKey);
        Future future = channel.writeAndFlush(rpcCmd).syncUninterruptibly();
        return future.isSuccess() ? EnumResponseState.success : EnumResponseState.fail;
    }

    public MessageDto request(String addressKey, RpcCmd rpcCmd, long timeout) {
        Channel channel = getChannel(addressKey);
        channel.writeAndFlush(rpcCmd);
        log.debug("await response");
        if (timeout < 1) {
            rpcCmd.await();
        } else {
            rpcCmd.await(timeout);
        }
        MessageDto res = rpcCmd.loadResult();
        log.debug("response is:{}", res);
        rpcCmd.loadRpcContent().clear();
        return res;
    }

    public MessageDto request(String addressKey, RpcCmd rpcCmd) {
        return request(addressKey, rpcCmd, -1);
    }

    /**
     * 判断指定socketAddress是否不存在连接
     *
     * @param socketAddress
     * @return
     */
    public boolean noChannel(SocketAddress socketAddress) {
        if (socketAddress == null) {
            return true;
        }
        String addressKey = socketAddress.toString();
        return channels.stream().noneMatch(
                channel -> addressKey.equals(channel.remoteAddress().toString())
        );
    }

    public int currentSize() {
        return channels.size();
    }

    public ChannelGroup getChannels() {
        return channels;
    }

    public List<String> loadAllRemoteKey() {
        return channels.stream().map(channel -> channel.remoteAddress().toString()).collect(Collectors.toList());
    }
}
