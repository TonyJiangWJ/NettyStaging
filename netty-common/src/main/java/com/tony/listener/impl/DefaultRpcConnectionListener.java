package com.tony.listener.impl;

import com.tony.listener.RpcConnectionListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class DefaultRpcConnectionListener implements RpcConnectionListener {
    @Override
    public void connect(ChannelHandlerContext ctx) {
        log.info("connected to: " + ctx.channel().remoteAddress().toString());
        saveConnect(ctx);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        log.info("disconnected from: " + ctx.channel().remoteAddress().toString());
    }
}
