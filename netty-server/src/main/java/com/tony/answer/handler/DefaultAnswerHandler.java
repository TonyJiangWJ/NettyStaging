package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;

/**
 * @author jiangwenjie 2021/2/22
 */
public class DefaultAnswerHandler extends ServerAnswerHandlerAdapter {
    public DefaultAnswerHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }
}
