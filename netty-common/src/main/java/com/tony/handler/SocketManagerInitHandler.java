package com.tony.handler;

import com.tony.constants.EnumNettyActions;
import com.tony.listener.RpcConnectionListener;
import com.tony.manager.SocketManager;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/22
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class SocketManagerInitHandler extends ChannelInboundHandlerAdapter {
    private RpcCmd heartCmd;

    @Autowired
    private RpcConnectionListener rpcConnectionListener;

    public SocketManagerInitHandler() {
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.HEART_CHECK.getActionKey());
        heartCmd = new RpcCmd();
        heartCmd.setMessage(messageDto);
        heartCmd.setRandomKey(String.valueOf(System.nanoTime()));
    }

    /**
     * 新建连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        rpcConnectionListener.connect(ctx.channel().remoteAddress().toString());
        SocketManager.getInstance().addChannel(ctx.channel());
    }

    /**
     * 连接停止，解除连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        String remoteKey = ctx.channel().remoteAddress().toString();
        rpcConnectionListener.disconnect(remoteKey);
        SocketManager.getInstance().removeChannel(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush(heartCmd);
            }
        }
    }
}
