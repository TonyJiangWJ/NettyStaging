package com.tony.serializer;

import com.tony.constants.EnumNettyProtoType;
import com.tony.serializer.impl.ProtobufSerializer;
import com.tony.serializer.impl.ProtostuffSerializer;
import com.tony.serializer.impl.PureProtobufSerializer;

/**
 * @author jiangwenjie 2020/11/14
 */
public class SerializerFactory {
    public static ObjectSerializer getSerializer(String protoType) {
        if (EnumNettyProtoType.PURE_PROTOBUF.getKey().equals(protoType)) {
            return PureProtobufSerializer.getInstance();
        } else if (EnumNettyProtoType.STUFF_PROTOBUF.getKey().equals(protoType)) {
            return ProtobufSerializer.getInstance();
        } else {
            return ProtostuffSerializer.getInstance();
        }
    }
}
