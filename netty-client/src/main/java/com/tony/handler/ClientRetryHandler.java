package com.tony.handler;

import com.tony.constants.EnumNettyActions;
import com.tony.initializer.RpcClientInitializer;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.UUID;

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
        heartCmd.setRandomKey(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyRpcClientInitializer.reconnect(ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof ConnectException) {
            Thread.sleep(15000);
            nettyRpcClientInitializer.reconnect(ctx.channel().remoteAddress());
        }
        ctx.writeAndFlush(heartCmd);
    }
}
