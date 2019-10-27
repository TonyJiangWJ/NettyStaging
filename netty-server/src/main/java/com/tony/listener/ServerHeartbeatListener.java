package com.tony.listener;

import com.alibaba.fastjson.JSON;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
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
        MessageDto messageDto = rpcCmd.getMessage();
        if (messageDto.getState() == EnumNettyState.REQUEST.getState()) {
            log.debug("服务端收到心跳请求：「{}」", JSON.toJSONString(rpcCmd));
        } else {
            log.debug("服务端收到心跳响应：「{}」", JSON.toJSONString(rpcCmd));
        }
    }
}
