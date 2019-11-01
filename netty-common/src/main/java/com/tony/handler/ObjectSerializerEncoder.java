package com.tony.handler;

import com.tony.message.RpcCmd;
import com.tony.serializer.ObjectSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class ObjectSerializerEncoder extends MessageToByteEncoder<RpcCmd> {

    private ObjectSerializer objectSerializer;

    public ObjectSerializerEncoder(ObjectSerializer objectSerializer) {
        this.objectSerializer = objectSerializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcCmd msg, ByteBuf out) throws Exception {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        log.debug("编码数据：{}", msg);
        try {
            objectSerializer.serialize(msg, bout);
        } catch (Exception e) {
            log.error("编码数据异常", e);
        }
    }
}
