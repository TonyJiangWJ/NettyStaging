package com.tony.listener;

import com.alibaba.fastjson.JSON;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
public class ServerHeartbeatListener implements HeartbeatListener {
    @Override
    public void onServerReceiveHeart(RpcCmd rpcCmd) {
        log.info("服务端收到心跳请求：「{}」cmdInfo:{}", JSON.toJSONString(rpcCmd.getMessage()), JSON.toJSONString(rpcCmd));
    }
}
