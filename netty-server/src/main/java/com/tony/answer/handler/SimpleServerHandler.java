package com.tony.answer.handler;

import com.alibaba.fastjson.JSON;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class SimpleServerHandler extends ServerAnswerHandlerAdapter {

    public SimpleServerHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    protected void doHandleRequest() {
        MessageDto message = new MessageDto();
        message.setAction(rpcCmd.getMessage().getAction());
        String data = rpcCmd.getMessage().dataOfClazz(String.class);
        if ("Hello，now is listClients".equals(data)) {
            message.setData(JSON.toJSONString(rpcClient.loadAllRemoteKey()));
        } else {
            message.setData("服务端执行请求成功, for:" + data.substring(13));
        }
        message.setState(EnumNettyState.RESPONSE_OK.getState());
        rpcCmd.setMessage(message);
        log.info("服务端执行完毕，发送反馈消息：「{}」", message);
        rpcClient.send(rpcCmd);
    }

}
