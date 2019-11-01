package com.tony.listener;

import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jiangwenjie 2019/10/22
 */
public interface HeartbeatListener {


    /**
     * 收到心跳请求的处理
     * @param ctx
     * @param rpcCmd
     */
    void handleReceiveHeart(ChannelHandlerContext ctx, RpcCmd rpcCmd);
}
