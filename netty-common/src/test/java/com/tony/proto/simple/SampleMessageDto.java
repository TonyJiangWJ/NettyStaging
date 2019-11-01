package com.tony.proto.simple;

import com.tony.constants.EnumNettyState;
import com.tony.serializer.impl.ProtobufSerializer;
import lombok.Data;

/**
 * @author jiangwenjie 2019/10/30
 */
@Data
public class SampleMessageDto {
    private String action;

    private int state = EnumNettyState.REQUEST.getState();

    private byte[] data;

    public <T> T dataOfClass(Class<T> clazz) {
        return ProtobufSerializer.getInstance().deSerialize(data, clazz);
    }

    public void setData(Object data) {
        this.data = ProtobufSerializer.getInstance().serialize(data);
    }
}
