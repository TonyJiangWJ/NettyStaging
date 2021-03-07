package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class Point2PointServerHandler extends ServerAnswerHandlerAdapter {

    public Point2PointServerHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    protected void doHandleRequest() {
        Point2PointMessage p2pMessage = rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class);
        String targetAddressKey = p2pMessage.getTargetAddressKey();
        String originAddressKey = rpcCmd.getRemoteAddressKey();
        p2pMessage.setTargetAddressKey(originAddressKey);
        MessageDto redirectMessage = new MessageDto();
        redirectMessage.setData(p2pMessage);
        redirectMessage.setAction(EnumNettyActions.P2P.getActionKey());
        redirectMessage.setState(EnumNettyState.REQUEST.getState());
        RpcCmd redirectCmd = new RpcCmd();
        redirectCmd.setRemoteAddressKey(targetAddressKey);
        redirectCmd.setRandomKey(RpcCmd.emptyKey());
        redirectCmd.setMessage(redirectMessage);

        String contentData = p2pMessage.getMessage();
        log.info("服务端转发：「{}」to「{}」的点对点消息: {}", originAddressKey, targetAddressKey, contentData);
        rpcClient.send(redirectCmd);
    }
}
