package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class SimpleHandler extends ClientAnswerHandlerAdapter {
    public SimpleHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    public void handleResponse() {
        super.handleResponse();
        log.info("客户端收到反馈消息：【{}】", rpcCmd.getMessage().dataOfClazz(String.class));
    }
}
