package com.tony.handler;

import com.alibaba.fastjson.JSON;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyType;
import com.tony.listener.HeartbeatListener;
import com.tony.message.NettyContext;
import com.tony.message.RpcCmd;
import com.tony.message.RpcContent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class RpcCmdDecoder extends MessageToMessageDecoder<RpcCmd> {

    @Autowired
    private HeartbeatListener heartbeatListener;

    @Override
    protected void decode(ChannelHandlerContext ctx, RpcCmd msg, List<Object> out) throws Exception {

        if (msg.getMessage() != null && EnumNettyActions.HEART_CHECK.getActionKey().equals(msg.getMessage().getAction())) {
            handlerHeartCheck(ctx, msg);
            return;
        }

        String randomKey = msg.getRandomKey();
        if (StringUtils.isEmpty(randomKey)) {
            log.debug("获取到的消息无randomKey：{}", msg);
            ctx.fireChannelRead(msg);
        } else {
            // 根据随机key 获取消息持有对象
            RpcContent content = msg.loadRpcContent();
            if (content != null) {
                log.debug("decode中获取到消息内容：「{}」", JSON.toJSONString(msg.getMessage()));
                // 设置消息结果
                content.setRes(msg.getMessage());
                // 发送信号解除等待
                log.debug("decode中发送signal");
                content.signal();
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    private void handlerHeartCheck(ChannelHandlerContext ctx, RpcCmd msg) {
        msg.setRemoteAddressKey(ctx.channel().remoteAddress().toString());
        if (NettyContext.getInstance().getNettyType().equals(EnumNettyType.client)) {
            heartbeatListener.onClientReceiveHeart(msg);
            ctx.writeAndFlush(msg);
        } else {
            heartbeatListener.onServerReceiveHeart(msg);
        }
    }
}
