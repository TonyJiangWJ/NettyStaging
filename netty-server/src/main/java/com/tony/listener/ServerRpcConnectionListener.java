package com.tony.listener;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author jiangwenjie 2019/10/26
 */
@Slf4j
@Component
public class ServerRpcConnectionListener implements RpcConnectionListener {

    @Autowired
    private RpcClient rpcClient;

    @Override
    public void connect(ChannelHandlerContext ctx) {
        saveConnect(ctx);
        String remoteKey = ctx.channel().remoteAddress().toString();
        log.info("new client[{}] connected, send broadcast to other clients", remoteKey);
        sendBroadcast("new client connected: " + remoteKey, key -> !remoteKey.equals(key));
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        String remoteKey = ctx.channel().remoteAddress().toString();
        log.info("client[{}] disconnected, send broadcast to other clients", remoteKey);
        sendBroadcast("client[" + remoteKey + "] disconnected", key -> !remoteKey.equals(key));
    }

    @Override
    public void authorizeConnection(ChannelHandlerContext ctx) {
        String remoteKey = ctx.channel().remoteAddress().toString();
        MessageDto messageDto = new MessageDto();
        messageDto.setState(EnumNettyState.REQUEST.getState());
        messageDto.setAction(EnumNettyActions.AUTHORIZE.getActionKey());
        String randomData = new RpcCmd().emptyKey();
        messageDto.setData(randomData);
        log.info("服务端发送认证请求到：" + remoteKey);
        rpcClient.requestAsync(ctx.channel(), messageDto, (msg) -> {
            try {
                if (msg != null) {
                    String resultStr = msg.dataOfClazz(String.class);
                    if (randomData.equals(resultStr)) {
                        log.info("链接认证成功：" + remoteKey);
                        MessageDto greetMessage = new MessageDto();
                        greetMessage.setAction(EnumNettyActions.NEW_CONNECT.getActionKey());
                        greetMessage.setState(EnumNettyState.RESPONSE_OK.getState());
                        greetMessage.setData("Welcome to connect nettyStaging server! " + remoteKey);
                        rpcClient.send(ctx.channel(), greetMessage);
                        this.connect(ctx);
                        return null;
                    }
                }
            } catch (Exception e) {
                log.error("连接认证处理异常", e);
            }
            ctx.close();
            log.error("链接认证失败，记录异常连接ip：" + remoteKey);
            return null;
        }, 5);
    }

    private void sendBroadcast(String message, Predicate<String> predicate) {
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.BROADCAST.getActionKey());
        messageDto.setState(EnumNettyState.RESPONSE_OK.getState());
        messageDto.setData(message);
        List<String> connectedClients = rpcClient.loadAllRemoteKey();
        connectedClients.stream()
                .filter(predicate)
                .forEach(key -> {
                    RpcCmd rpcCmd = new RpcCmd();
                    rpcCmd.setRemoteAddressKey(key);
                    rpcCmd.setMessage(messageDto);
                    rpcCmd.setRandomKey(rpcCmd.emptyKey());
                    rpcClient.send(rpcCmd);
                });
    }

}
