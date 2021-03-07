package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class NewConnectHandler extends ClientAnswerHandlerAdapter {

    public NewConnectHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    public void handleResponse() {
        super.handleResponse();
        log.info("客户端建立连接成功：{}", rpcCmd.getMessage().dataOfClazz(String.class));
    }
}
