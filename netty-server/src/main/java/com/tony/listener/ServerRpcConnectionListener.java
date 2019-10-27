package com.tony.listener;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/26
 */
@Slf4j
@Component
public class ServerRpcConnectionListener implements RpcConnectionListener {

    @Autowired
    private RpcClient rpcClient;

    @Override
    public void connect(String remoteKey) {
        log.info("new client[{}] connected, send broadcast to other clients", remoteKey);
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.BROADCAST.getActionKey());
        messageDto.setState(EnumNettyState.RESPONSE_OK.getState());
        messageDto.setData("new client connected: " + remoteKey);
        List<String> connectedClients = rpcClient.loadAllRemoteKey();
        connectedClients.stream()
                .filter(key -> !remoteKey.equals(key))
                .forEach(key -> {
                    RpcCmd rpcCmd = new RpcCmd();
                    rpcCmd.setRemoteAddressKey(key);
                    rpcCmd.setMessage(messageDto);
                    rpcCmd.setRandomKey(rpcCmd.emptyKey());
                    rpcClient.send(rpcCmd);
                });
    }

    @Override
    public void disconnect(String remoteKey) {

    }
}
