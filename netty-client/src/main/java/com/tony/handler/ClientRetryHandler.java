package com.tony.handler;

import com.tony.constants.EnumNettyActions;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

/**
 * @author jiangwenjie 2019/10/23
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class ClientRetryHandler extends ChannelInboundHandlerAdapter {

    private RpcCmd heartCmd;

    private ReconnectManager reconnectManager;


    @Autowired
    public ClientRetryHandler(@Lazy ReconnectManager reconnectManager) {
        this.reconnectManager = reconnectManager;
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.HEART_CHECK.getActionKey());
        heartCmd = new RpcCmd();
        heartCmd.setMessage(messageDto);
        heartCmd.setRandomKey(RpcCmd.emptyKey());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.warn("channelInactive 客户端与「{}」连接断开，等待重连", ctx.channel().remoteAddress());
        reconnectManager.reconnect(ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ConnectException) {
            log.warn("客户端与「{}」连接断开，等待重连", ctx.channel().remoteAddress(), cause);
            Thread.sleep(15000);
            reconnectManager.reconnect(ctx.channel().remoteAddress());
        } else {
            log.warn("客户端与「{}」之间发生异常，发送心跳请求", ctx.channel().remoteAddress(), cause);
            ctx.writeAndFlush(heartCmd);
        }
    }
}
