package com.tony.message;

import com.tony.constants.EnumNettyProtoType;
import com.tony.constants.EnumNettyState;
import com.tony.serializer.impl.ProtobufSerializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 消息对象
 *
 * @author jiangwenjie 2019/10/22
 */
@Slf4j
@Data
@ToString
@EqualsAndHashCode
public class MessageDto implements Serializable {

    private String action;

    private int state = EnumNettyState.REQUEST.getState();

    /**
     * 跨语言使用Protostuff中提供的protobuff序列化传递复杂对象
     */
    private byte[] bytesData;

    private Serializable serialData;

    private transient boolean isFromBuff;


    public <T> T dataOfClazz(Class<T> clazz) {
        if (EnumNettyProtoType.STUFF_PROTOBUF.getKey().equals(NettyContext.getInstance().getProtoType())) {
            return bytesDataOfClass(clazz);
        } else {
            return serialDataOfClazz(clazz);
        }
    }

    public <T extends Serializable> void setData(T object) {
        if (EnumNettyProtoType.STUFF_PROTOBUF.getKey().equals(NettyContext.getInstance().getProtoType())) {
            setBytesData(object);
        } else {
            setSerialData(object);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T serialDataOfClazz(Class<T> clazz) {
        if (serialData == null) {
            return null;
        }
        if (clazz.isInstance(serialData)) {
            return (T) serialData;
        } else {
            throw new IllegalArgumentException("data is not instance of class:" + clazz.getName());
        }
    }

    private <T> T bytesDataOfClass(Class<T> clazz) {
        if (bytesData == null) {
            return null;
        }
        try {
            return ProtobufSerializer.getInstance().deSerialize(bytesData, clazz);
        } catch (Exception e) {
            log.error("反序列化data对象失败，请确认对象是否为：{} 类型", clazz);
        }
        return null;
    }

    private <T extends Serializable> void setBytesData(T data) {
        this.bytesData = ProtobufSerializer.getInstance().serialize(data);
    }
}
