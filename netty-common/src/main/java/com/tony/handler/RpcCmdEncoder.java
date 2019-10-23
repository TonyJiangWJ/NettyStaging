package com.tony.handler;

import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
@ChannelHandler.Sharable
@Slf4j
public class RpcCmdEncoder extends MessageToMessageEncoder<RpcCmd> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcCmd msg, List<Object> out) throws Exception {
        out.add(msg);
    }
}
