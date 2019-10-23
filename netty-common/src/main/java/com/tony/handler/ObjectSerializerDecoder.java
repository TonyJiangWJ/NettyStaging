package com.tony.handler;

import com.tony.message.RpcCmd;
import com.tony.serializer.impl.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
public class ObjectSerializerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null) {
            return;
        }
        ByteBufInputStream inputStream = new ByteBufInputStream(in);
        RpcCmd cmd = ProtostuffSerializer.getInstance().deSerialize(inputStream, RpcCmd.class);
        out.add(cmd);
    }
}
