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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @author jiangwenjie 2019/10/22
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class SocketManagerInitHandler extends ChannelInboundHandlerAdapter {
    private RpcCmd heartCmd;

    @Value("${netty.blocked:''}")
    private Set<String> blockedIps;

    @Autowired
    private RpcConnectionListener rpcConnectionListener;

    public SocketManagerInitHandler() {
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.HEART_CHECK.getActionKey());
        heartCmd = new RpcCmd();
        heartCmd.setMessage(messageDto);
        heartCmd.setRandomKey(RpcCmd.emptyKey());
    }

    /**
     * 新建连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        if (blockedIps.size() > 0 && inetSocketAddress != null) {
            if (blockedIps.contains(inetSocketAddress.getHostString())) {
                ctx.close();
                return;
            }
        }
        rpcConnectionListener.authorizeConnection(ctx);
    }

    /**
     * 连接停止，解除连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        if (!SocketManager.getInstance().noChannel(ctx.channel().remoteAddress())) {
            rpcConnectionListener.disconnect(ctx);
        }
        SocketManager.getInstance().removeChannel(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.writeAndFlush(heartCmd);
            }
        }
    }
}
