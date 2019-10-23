package com.tony.serializer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jiangwenjie 2019/10/23
 */
public interface ObjectSerializer {
    /**
     * 序列化对象为输出流
     *
     * @param obj          需要序更列化的对象
     * @param outputStream 写入对象
     */
    void serialize(Object obj, OutputStream outputStream);


    /**
     * 序列化对象为二进制
     *
     * @param obj 需要序更列化的对象
     * @return byte []  序列号结果
     */
    byte[] serialize(Object obj);


    /**
     * 将输入流反序列化为对象
     *
     * @param inputStream 需要反序列化的inputStream
     * @param clazz       反序列化成为的bean对象Class
     * @param <T>         反序列化成为的bean对象
     * @return 对象
     */
    <T> T deSerialize(InputStream inputStream, Class<T> clazz);


    /**
     * 二进制数组反序列化为对象
     *
     * @param param 需要反序列化的byte []
     * @param clazz 反序列化成为的bean对象Class
     * @param <T>   反序列化成为的bean对象
     * @return 对象
     */
    <T> T deSerialize(byte[] param, Class<T> clazz);

}
