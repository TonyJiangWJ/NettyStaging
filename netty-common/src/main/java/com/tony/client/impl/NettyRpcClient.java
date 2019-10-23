package com.tony.client.impl;

import com.tony.client.RpcClient;
import com.tony.constants.EnumResponseState;
import com.tony.manager.SocketManager;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
@Component
@Slf4j
public class NettyRpcClient implements RpcClient {
    @Override
    public EnumResponseState send(RpcCmd rpcCmd) {
        return SocketManager.getInstance().send(rpcCmd);
    }

    @Override
    public EnumResponseState send(String remoteKey, MessageDto messageDto) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setRemoteAddressKey(remoteKey);
        rpcCmd.setMessage(messageDto);
        return SocketManager.getInstance().send(remoteKey, rpcCmd);
    }

    @Override
    public MessageDto request(RpcCmd rpcCmd) {
        return request0(rpcCmd, -1);
    }

    @Override
    public MessageDto request(RpcCmd rpcCmd, long timeout) {
        return request0(rpcCmd, timeout);
    }

    @Override
    public MessageDto request(String remoteKey, MessageDto messageDto) {
        return request(remoteKey, messageDto, -1);
    }

    @Override
    public MessageDto request(String remoteKey, MessageDto messageDto, long timeout) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setRemoteAddressKey(remoteKey);
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRandomKey(rpcCmd.randomKey());
        return request0(rpcCmd, timeout);
    }

    private MessageDto request0(RpcCmd rpcCmd, long timeout) {
        if (StringUtils.isEmpty(rpcCmd.getRandomKey())) {
            // 请求需要通过randomKey获取上下文消息持有对象，因此randomKey必传
            throw new IllegalArgumentException("random key must not be null");
        }
        return SocketManager.getInstance().request(rpcCmd.getRemoteAddressKey(), rpcCmd, timeout);
    }

    @Override
    public List<String> loadAllRemoteKey() {
        return SocketManager.getInstance().loadAllRemoteKey();
    }
}
