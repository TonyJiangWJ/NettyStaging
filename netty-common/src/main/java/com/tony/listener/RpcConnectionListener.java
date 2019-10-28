package com.tony.listener;

import com.tony.manager.SocketManager;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jiangwenjie 2019/10/22
 */
public interface RpcConnectionListener {

    /**
     * 建立连接
     *
     * @param ctx
     */
    void connect(ChannelHandlerContext ctx);

    /**
     * 解除连接
     *
     * @param ctx
     */
    void disconnect(ChannelHandlerContext ctx);

    /**
     * 认证连接，未认证的断开
     *
     * @param ctx
     * @return
     */
    default void authorizeConnection(ChannelHandlerContext ctx) {
        this.connect(ctx);
    }

    /**
     * 保存连接
     *
     * @param ctx
     */
    default void saveConnect(ChannelHandlerContext ctx) {
        SocketManager.getInstance().addChannel(ctx.channel());
    }
}
