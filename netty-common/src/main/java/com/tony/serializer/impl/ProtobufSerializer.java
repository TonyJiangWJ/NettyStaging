package com.tony.serializer.impl;

import com.tony.serializer.ObjectSerializer;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.RuntimeSchema;
import lombok.extern.slf4j.Slf4j;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 基于Protostuff优化版的ProtobufIOUtil实现序列化，理论上可以支持跨语言序列化
 *
 * @author jiangwenjie 2019/10/30
 */
@Slf4j
public class ProtobufSerializer implements ObjectSerializer {


    private final static Objenesis OBJENESIS = new ObjenesisStd(true);

    private ProtobufSerializer() {
    }

    private static class SingletonHolder {
        final static ProtobufSerializer INSTANCE = new ProtobufSerializer();
    }

    public static ProtobufSerializer getInstance() {
        return ProtobufSerializer.SingletonHolder.INSTANCE;
    }

    @Override
    public void serialize(Object obj, OutputStream outputStream) {
        Class clz = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(clz);
            ProtobufIOUtil.writeTo(outputStream, obj, schema, buffer);
        } catch (IOException e) {
            log.error("序列化对象失败", e);
        } finally {
            buffer.clear();
        }
    }

    @Override
    public byte[] serialize(Object obj) {
        Class clz = obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            Schema schema = getSchema(clz);
            ProtobufIOUtil.writeTo(arrayOutputStream, obj, schema, buffer);
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
        T object = OBJENESIS.newInstance(clazz);
        Schema<T> schema = getSchema(clazz);
        try {
            ProtobufIOUtil.mergeFrom(inputStream, object, schema);
            return object;
        } catch (IOException e) {
            log.error("反序列化对象失败", e);
        }
        return null;
    }

    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) {
        T object = OBJENESIS.newInstance(clazz);
        Schema<T> schema = getSchema(clazz);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            ProtobufIOUtil.mergeFrom(inputStream, object, schema);
            return object;
        } catch (IOException e) {
            log.error("反序列化对象失败", e);
        }
        return null;
    }


    private <T> Schema<T> getSchema(Class<T> clz) {
        return RuntimeSchema.createFrom(clz, new DefaultIdStrategy());
    }
}
