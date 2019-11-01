package com.tony.serializer.impl;

import com.google.protobuf.Message;
import com.tony.message.RpcCmd;
import com.tony.message.protobuff.RpcCmdOuterClass;
import com.tony.message.protobuff.util.PojoConverterUtil;
import com.tony.serializer.ObjectSerializer;
import io.protostuff.LinkedBuffer;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 纯Protobuff实现，支持跨语言序列化
 *
 * @author jiangwenjie 2019/10/30
 */
@Slf4j
public class PureProtobufSerializer implements ObjectSerializer {

    private PureProtobufSerializer() {
    }

    private static class SingletonHolder {
        final static PureProtobufSerializer INSTANCE = new PureProtobufSerializer();
    }

    public static PureProtobufSerializer getInstance() {
        return PureProtobufSerializer.SingletonHolder.INSTANCE;
    }


    @Override
    public void serialize(Object obj, OutputStream outputStream) {
        Message message = PojoConverterUtil.parseToProtobuff((Serializable) obj);
        try {
            message.writeTo(outputStream);
        } catch (IOException e) {
            log.error("序列化异常", e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public byte[] serialize(Object obj) {
        Message message = PojoConverterUtil.parseToProtobuff((Serializable) obj);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            message.writeTo(arrayOutputStream);
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("序列化对象失败", e);
        } finally {
            buffer.clear();
        }
        return new byte[0];
    }

    @Override
    public <T> T deSerialize(InputStream inputStream, Class<T> clazz) {
        if (!clazz.isAssignableFrom(RpcCmd.class)) {
            throw new IllegalArgumentException("RpcCmd support only!");
        }
        try {
            RpcCmdOuterClass.RpcCmd rpcCmd = RpcCmdOuterClass.RpcCmd.parseFrom(inputStream);
            return PojoConverterUtil.convertFromProtoBuff(rpcCmd, clazz);
        } catch (IOException e) {
            log.error("反序列化对象失败", e);
        }
        return null;
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) {
        if (!clazz.isAssignableFrom(RpcCmd.class)) {
            throw new IllegalArgumentException("RpcCmd support only!");
        }
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            RpcCmdOuterClass.RpcCmd rpcCmd = RpcCmdOuterClass.RpcCmd.parseFrom(inputStream);
            return PojoConverterUtil.convertFromProtoBuff(rpcCmd, clazz);
        } catch (IOException e) {
            log.error("反序列化对象失败", e);
        }
        return null;
    }
}
