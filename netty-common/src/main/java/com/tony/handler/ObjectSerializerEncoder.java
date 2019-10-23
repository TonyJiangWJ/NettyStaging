package com.tony.handler;

import com.tony.message.RpcCmd;
import com.tony.serializer.impl.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author jiangwenjie 2019/10/23
 */
public class ObjectSerializerEncoder extends MessageToByteEncoder<RpcCmd> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcCmd msg, ByteBuf out) throws Exception {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        ProtostuffSerializer.getInstance().serialize(msg, bout);
    }
}
