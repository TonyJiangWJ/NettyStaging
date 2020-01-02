package com.tony.handler;

import com.tony.constants.EnumNettyType;
import com.tony.message.NettyContext;
import com.tony.message.RpcCmd;
import com.tony.serializer.ObjectSerializer;
import com.tony.util.AESEncrypt;
import com.tony.util.RSAEncrypt;
import com.tony.util.RSAUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class ObjectSerializerDecoder extends ByteToMessageDecoder {

    private ObjectSerializer objectSerializer;
    private RSAUtil rsaUtil;

    public ObjectSerializerDecoder(ObjectSerializer objectSerializer, RSAUtil rsaUtil) {
        this.objectSerializer = objectSerializer;
        this.rsaUtil = rsaUtil;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in == null) {
            return;
        }
        ByteBufInputStream inputStream = new ByteBufInputStream(in);
        if (rsaUtil != null) {
            byte[] buff = new byte[4096];
            byte[] bytesContent = new byte[0];
            int len = 0;
            while ((len = inputStream.read(buff)) > 0) {
                int originLength = bytesContent.length;
                bytesContent = expandArray(bytesContent, originLength + len);
                System.arraycopy(buff, 0, bytesContent, originLength, len);
            }
            byte[] encryptKey = new byte[128];
            System.arraycopy(bytesContent, 0, encryptKey, 0, 128);
            byte[] originKey = null;
            if (NettyContext.getInstance().getNettyType().equals(EnumNettyType.client)) {
                originKey = rsaUtil.decryptByPublicKey(encryptKey);
            } else {
                originKey = rsaUtil.decryptByPrivateKey(encryptKey);
            }
            log.debug("加密后的key:{} 解密后的key:{}", RSAEncrypt.byteArrayToHEXString(encryptKey), RSAEncrypt.byteArrayToHEXString(originKey));

            byte[] encryptContentData = new byte[bytesContent.length - 128];
            System.arraycopy(bytesContent, 128, encryptContentData, 0, encryptContentData.length);
            bytesContent = AESEncrypt.decrypt(encryptContentData, AESEncrypt.loadKeyByBytes(originKey));
            if (bytesContent != null) {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytesContent);
                try {
                    RpcCmd cmd = objectSerializer.deSerialize(byteArrayInputStream, RpcCmd.class);
                    out.add(cmd);
                } catch (Exception e) {
                    log.error("解码数据异常", e);
                }
            } else {
                log.error("解密数据异常");
            }
        } else {
            try {
                RpcCmd cmd = objectSerializer.deSerialize(inputStream, RpcCmd.class);
                out.add(cmd);
            } catch (Exception e) {
                log.error("解码数据异常", e);
            }
        }
    }

    private byte[] expandArray(byte[] origin, int targetLength) {
        byte[] newBytes = new byte[targetLength];
        System.arraycopy(origin, 0, newBytes, 0, origin.length);
        return newBytes;
    }
}
