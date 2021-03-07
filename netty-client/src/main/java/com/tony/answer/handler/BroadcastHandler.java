package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import lombok.extern.slf4j.Slf4j;

import static com.tony.constants.EnumNettyActions.BROADCAST;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class BroadcastHandler extends ClientAnswerHandlerAdapter {
    public BroadcastHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    protected void doHandleRequest() {
        MessageDto messageDto = new MessageDto();
        messageDto.setData("ok, i know");
        messageDto.setState(EnumNettyState.RESPONSE_OK.getState());
        messageDto.setAction(BROADCAST.getActionKey());
        rpcCmd.setMessage(messageDto);
        log.info("客户端响应广播消息：" + rpcCmd.getMessage());
        rpcClient.send(rpcCmd);
    }

    @Override
    public void handleResponse() {
        super.handleResponse();
        String content = rpcCmd.getMessage().dataOfClazz(String.class);
        if (content.contains("new client")) {
            String newClientKey = content.substring(22);
            Point2PointMessage point2PointMessage = new Point2PointMessage();
            point2PointMessage.setMessage("Hello, new client. I'm old client");
            point2PointMessage.setTargetAddressKey(newClientKey);
            MessageDto messageDto = new MessageDto();
            messageDto.setState(EnumNettyState.REQUEST.getState());
            messageDto.setAction(EnumNettyActions.P2P.getActionKey());
            messageDto.setData(point2PointMessage);
            RpcCmd sendCmd = new RpcCmd();
            sendCmd.setMessage(messageDto);
            sendCmd.setRandomKey(RpcCmd.emptyKey());
            sendCmd.setRemoteAddressKey(rpcCmd.getRemoteAddressKey());
            log.info("客户端发送hello信息到新客户端");
            rpcClient.send(sendCmd);
        }

    }
}
