package com.tony.client.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.RpcException;
import com.tony.client.RpcClient;
import com.tony.constants.EnumResponseState;
import com.tony.manager.SocketManager;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.util.Callback;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangwenjie 2019/10/23
 */
@Component
@Slf4j
public class NettyRpcClient implements RpcClient {

    private final ExecutorService executorService;

    @Autowired
    public NettyRpcClient() {
        int threadNum = Runtime.getRuntime().availableProcessors() * 5;
        this.executorService = new ThreadPoolExecutor(
                threadNum, threadNum,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("async-rpc-call-%d").build()
        );
    }

    @Override
    public EnumResponseState send(RpcCmd rpcCmd) {
        return SocketManager.getInstance().send(rpcCmd);
    }

    @Override
    public EnumResponseState send(String remoteKey, MessageDto messageDto) {
        if (StringUtils.isEmpty(remoteKey)) {
            throw new IllegalArgumentException("remoteKey can not be null");
        }
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setRemoteAddressKey(remoteKey);
        rpcCmd.setMessage(messageDto);
        return SocketManager.getInstance().send(remoteKey, rpcCmd);
    }

    @Override
    public EnumResponseState send(Channel channel, MessageDto messageDto) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setMessage(messageDto);
        return SocketManager.getInstance().send(channel, rpcCmd);
    }

    @Override
    public MessageDto request(RpcCmd rpcCmd) throws RpcException {
        return request0(rpcCmd, -1);
    }

    @Override
    public MessageDto request(RpcCmd rpcCmd, long timeout) throws RpcException {
        return request0(rpcCmd, timeout);
    }

    @Override
    public MessageDto request(String remoteKey, MessageDto messageDto) throws RpcException {
        return request(remoteKey, messageDto, -1);
    }

    @Override
    public MessageDto request(String remoteKey, MessageDto messageDto, long timeout) throws RpcException {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setRemoteAddressKey(remoteKey);
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRandomKey(rpcCmd.randomKey());
        return request0(rpcCmd, timeout);
    }

    private MessageDto request0(RpcCmd rpcCmd, long timeout) throws RpcException {
        if (StringUtils.isEmpty(rpcCmd.getRandomKey())) {
            // 请求需要通过randomKey获取上下文消息持有对象，因此randomKey必传
            throw new IllegalArgumentException("random key must not be null");
        }
        return SocketManager.getInstance().request(rpcCmd.getRemoteAddressKey(), rpcCmd, timeout);
    }

    private MessageDto request0(Channel channel, RpcCmd rpcCmd, long timeout) throws RpcException {
        if (StringUtils.isEmpty(rpcCmd.getRandomKey())) {
            // 请求需要通过randomKey获取上下文消息持有对象，因此randomKey必传
            throw new IllegalArgumentException("random key must not be null");
        }
        return SocketManager.getInstance().request(channel, rpcCmd, timeout);
    }

    @Override
    public void requestAsync(RpcCmd rpcCmd, Callback<MessageDto, Void> callback) {
        requestAsync0(rpcCmd, -1, callback);
    }

    @Override
    public void requestAsync(RpcCmd rpcCmd, Callback<MessageDto, Void> callback, long timeout) {
        requestAsync0(rpcCmd, timeout, callback);
    }

    @Override
    public void requestAsync(String remoteKey, MessageDto messageDto, Callback<MessageDto, Void> callback) {
        requestAsync(remoteKey, messageDto, callback, -1);
    }

    @Override
    public void requestAsync(String remoteKey, MessageDto messageDto, Callback<MessageDto, Void> callback, long timeout) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRemoteAddressKey(remoteKey);
        rpcCmd.setRandomKey(rpcCmd.randomKey());
        requestAsync0(rpcCmd, timeout, callback);
    }

    @Override
    public void requestAsync(Channel channel, MessageDto messageDto, Callback<MessageDto, Void> callback, long timeout) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRandomKey(rpcCmd.randomKey());
        requestAsync0(channel, rpcCmd, timeout, callback);
    }

    private void requestAsync0(RpcCmd rpcCmd, long timeout, Callback<MessageDto, Void> callback) {
        if (StringUtils.isEmpty(rpcCmd.getRemoteAddressKey())) {
            throw new IllegalArgumentException("remoteAddressKey can not be empty");
        }
        executorService.submit(() -> {
            MessageDto responseMsg = null;
            try {
                responseMsg = request0(rpcCmd, timeout);
            } catch (RpcException e) {
                log.error("异步请求执行失败", e);
            }
            callback.call(responseMsg);
        });
    }

    private void requestAsync0(Channel channel, RpcCmd rpcCmd, long timeout, Callback<MessageDto, Void> callback) {
        executorService.submit(() -> {
            MessageDto responseMsg = null;
            try {
                responseMsg = request0(channel, rpcCmd, timeout);
            } catch (RpcException e) {
                log.error("异步请求执行失败", e);
            }
            callback.call(responseMsg);
        });
    }

    @Override
    public List<String> loadAllRemoteKey() {
        return SocketManager.getInstance().loadAllRemoteKey();
    }
}
