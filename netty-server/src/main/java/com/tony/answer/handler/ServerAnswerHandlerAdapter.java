package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class ServerAnswerHandlerAdapter extends AbstractAnswerHandler {
    public ServerAnswerHandlerAdapter(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    protected void doHandleRequest() {

    }

    @Override
    public void handleResponse() {
        MessageDto message = rpcCmd.getMessage();
        String actionKey = message.getAction();
        log.info("服务端收到{}消息：【{}】", EnumNettyActions.getActionByKey(actionKey).getActionName(), message);
    }
}
