package com.tony.handler;

import com.tony.constants.EnumNettyActions;
import com.tony.initializer.RpcClientInitializer;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

/**
 * @author jiangwenjie 2019/10/23
 */
@ChannelHandler.Sharable
@Slf4j
@Component
@DependsOn("nettyRpcClientInitializer")
public class ClientRetryHandler extends ChannelInboundHandlerAdapter {

    private RpcCmd heartCmd;

    private RpcClientInitializer nettyRpcClientInitializer;


    @Autowired
    public ClientRetryHandler(RpcClientInitializer rpcClientInitializer) {
        this.nettyRpcClientInitializer = rpcClientInitializer;
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.HEART_CHECK.getActionKey());
        heartCmd = new RpcCmd();
        heartCmd.setMessage(messageDto);
        heartCmd.setRandomKey(heartCmd.emptyKey());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyRpcClientInitializer.reconnect(ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ConnectException) {
            log.warn("客户端与「{}」连接断开，等待重连", ctx.channel().remoteAddress());
            Thread.sleep(15000);
            nettyRpcClientInitializer.reconnect(ctx.channel().remoteAddress());
        }
        log.info("客户端与「{}」之间发生异常，发送心跳请求", ctx.channel().remoteAddress());
        ctx.writeAndFlush(heartCmd);
    }
}
