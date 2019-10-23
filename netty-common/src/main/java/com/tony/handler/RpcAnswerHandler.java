package com.tony.handler;

import com.tony.answer.RpcAnswer;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/22
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class RpcAnswerHandler extends SimpleChannelInboundHandler<RpcCmd> {

    @Autowired
    private RpcAnswer rpcAnswer;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcCmd rpcCmd) throws Exception {
        String remoteAddressKey = channelHandlerContext.channel().remoteAddress().toString();
        rpcCmd.setRemoteAddressKey(remoteAddressKey);
        rpcAnswer.callback(rpcCmd);
    }
}
