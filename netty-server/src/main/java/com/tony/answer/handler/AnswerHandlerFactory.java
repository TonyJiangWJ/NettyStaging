package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.message.RpcCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2021/2/22
 */
@Component
public class AnswerHandlerFactory {
    @Autowired
    private RpcClient rpcClient;
    public AbstractAnswerHandler getHandler(RpcCmd rpcCmd) {
        String action = rpcCmd.getMessage().getAction();
        EnumNettyActions nettyAction = EnumNettyActions.getActionByKey(action);
        switch (nettyAction) {
            case P2P:
                return new Point2PointServerHandler(rpcClient, rpcCmd);
            case SIMPLE_ACTION:
                return new SimpleServerHandler(rpcClient, rpcCmd);
            default:
                return new DefaultAnswerHandler(rpcClient, rpcCmd);
        }
    }
}
