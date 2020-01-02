package com.tony.handler;

import com.tony.constants.EnumNettyType;
import com.tony.message.NettyContext;
import com.tony.message.RpcCmd;
import com.tony.serializer.ObjectSerializer;
import com.tony.util.AESEncrypt;
import com.tony.util.RSAEncrypt;
import com.tony.util.RSAUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class ObjectSerializerEncoder extends MessageToByteEncoder<RpcCmd> {

    private ObjectSerializer objectSerializer;
    private RSAUtil rsaUtil;

    public ObjectSerializerEncoder(ObjectSerializer objectSerializer, RSAUtil rsaUtil) {
        this.objectSerializer = objectSerializer;
        this.rsaUtil = rsaUtil;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, RpcCmd msg, ByteBuf out) throws Exception {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        log.debug("编码数据：{}", msg);
        if (rsaUtil != null) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                objectSerializer.serialize(msg, outputStream);
                byte[] dataBytes = outputStream.toByteArray();
                byte[] originKey = AESEncrypt.generateAesKey();
                byte[] encryptKey = null;
                if (NettyContext.getInstance().getNettyType().equals(EnumNettyType.client)) {
                    encryptKey = this.rsaUtil.encryptWithPubKey(originKey);
                } else {
                    encryptKey = this.rsaUtil.encryptWithPrivateKey(originKey);
                }
                dataBytes = AESEncrypt.encrypt(dataBytes, AESEncrypt.loadKeyByBytes(originKey));
                // 写入加密的aes key
                bout.write(encryptKey);
                log.debug("aes key:{} 加密后的key:{}", RSAEncrypt.byteArrayToHEXString(originKey), RSAEncrypt.byteArrayToHEXString(encryptKey));
                // 写入加密的内容
                bout.write(dataBytes);
            } catch (Exception e) {
                log.error("编码数据异常", e);
            }
        } else {
            try {
                objectSerializer.serialize(msg, bout);
            } catch (Exception e) {
                log.error("编码数据异常", e);
            }
        }
    }
}
