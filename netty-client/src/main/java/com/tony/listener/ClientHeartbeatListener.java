package com.tony.listener;

import com.alibaba.fastjson.JSON;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
public class ClientHeartbeatListener implements HeartbeatListener {
    @Override
    public void handleReceiveHeart(ChannelHandlerContext ctx, RpcCmd rpcCmd) {
        rpcCmd.setRemoteAddressKey(ctx.channel().remoteAddress().toString());
        MessageDto messageDto = rpcCmd.getMessage();
        if (messageDto.getState() == EnumNettyState.REQUEST.getState()) {
            log.debug("客户端收到来自【{}】心跳请求，返回心跳信息", rpcCmd.getRemoteAddressKey());
            rpcCmd.getMessage().setData("hello, im client");
            rpcCmd.getMessage().setState(EnumNettyState.RESPONSE_OK.getState());
        } else {
            log.debug("客户端收到来自【{}】心跳响应：「{}」", rpcCmd.getRemoteAddressKey(), JSON.toJSONString(rpcCmd));
        }
        // 客户端收到心跳请求时，进行响应
        ctx.writeAndFlush(rpcCmd);
    }
}
