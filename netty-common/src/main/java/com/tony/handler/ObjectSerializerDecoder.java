package com.tony.handler;

import com.tony.message.RpcCmd;
import com.tony.serializer.ObjectSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class ObjectSerializerDecoder extends ByteToMessageDecoder {

    private ObjectSerializer objectSerializer;

    public ObjectSerializerDecoder(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null) {
            return;
        }
        ByteBufInputStream inputStream = new ByteBufInputStream(in);
        try {
            RpcCmd cmd = objectSerializer.deSerialize(inputStream, RpcCmd.class);
            out.add(cmd);
        } catch (Exception e) {
            log.error("解码数据异常", e);
        }
    }
}
