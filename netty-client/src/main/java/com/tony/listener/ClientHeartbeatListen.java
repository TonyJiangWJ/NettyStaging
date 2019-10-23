package com.tony.listener;

import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
public class ClientHeartbeatListen implements HeartbeatListener {
    @Override
    public void onClientReceiveHeart(RpcCmd rpcCmd) {
        log.info("客户端收到来自【{}】心跳请求，返回心跳信息", rpcCmd.getRemoteAddressKey());
        rpcCmd.getMessage().setData("hello, im client");
    }
}
