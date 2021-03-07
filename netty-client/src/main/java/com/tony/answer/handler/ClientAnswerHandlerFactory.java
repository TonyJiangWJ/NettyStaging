package com.tony.answer.handler;

import com.tony.authorize.AuthorizeService;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.message.RpcCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2021/2/22
 */
@Component
public class ClientAnswerHandlerFactory {

    @Autowired
    private AuthorizeService authorizeService;
    @Autowired
    private RpcClient rpcClient;

    public AbstractAnswerHandler getHandler(RpcCmd rpcCmd) {
        String action = rpcCmd.getMessage().getAction();
        EnumNettyActions nettyAction = EnumNettyActions.getActionByKey(action);
        switch (nettyAction) {
            case P2P:
                return new Point2PointHandler(rpcClient, rpcCmd);
            case AUTHORIZE:
                AuthorizeHandler handler = new AuthorizeHandler(rpcClient, rpcCmd);
                handler.setAuthorizeService(authorizeService);
                return handler;
            case BROADCAST:
                return new BroadcastHandler(rpcClient, rpcCmd);
            case SIMPLE_ACTION:
                return new SimpleHandler(rpcClient, rpcCmd);
            case NEW_CONNECT:
                return new NewConnectHandler(rpcClient, rpcCmd);
            default:
                return new DefaultHandler(rpcClient, rpcCmd);
        }
    }
}
