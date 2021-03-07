package com.tony.message.protobuff.util;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import com.tony.message.protobuff.BytesDataOuterClass;
import com.tony.message.protobuff.MessageDtoOuterClass;
import com.tony.message.protobuff.Point2PointMessageOuterClass;
import com.tony.message.protobuff.RpcCmdOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * 纯Protobuff实现时的对象转换工具
 * @author jiangwenjie 2019/10/30
 */
@Slf4j
@SuppressWarnings("unchecked")
public class PojoConverterUtil {


    public static <T, E extends Message> T convertFromProtoBuff(E origin, Class<T> clazz) {
        long first = 1000000000000L;
        if (clazz.isAssignableFrom(Point2PointMessage.class)) {
            return (T)convertFromProtoBuff((Point2PointMessageOuterClass.Point2PointMessage)origin);
        } else if (clazz.isAssignableFrom(MessageDto.class)) {
            return (T)convertFromProtoBuff((MessageDtoOuterClass.MessageDto)origin);
        } else if (clazz.isAssignableFrom(RpcCmd.class)) {
            return (T)convertFromProtoBuff((RpcCmdOuterClass.RpcCmd)origin);
        } else {
            throw new IllegalArgumentException("can not find suitable conversion");
        }
    }


    public static <T extends Serializable, E extends Message> T convertFromProtoBuff(E origin) {
        if (origin instanceof Point2PointMessageOuterClass.Point2PointMessage) {
            return (T)convertFromProtoBuff((Point2PointMessageOuterClass.Point2PointMessage)origin);
        } else if (origin instanceof MessageDtoOuterClass.MessageDto) {
            return (T)convertFromProtoBuff((MessageDtoOuterClass.MessageDto)origin);
        } else if (origin instanceof RpcCmdOuterClass.RpcCmd) {
            return (T)convertFromProtoBuff((RpcCmdOuterClass.RpcCmd)origin);
        } else if (origin instanceof BytesDataOuterClass.BytesData) {
            return (T)convertFromProtoBuff((BytesDataOuterClass.BytesData)origin);
        } else {
            throw new IllegalArgumentException("can not find suitable conversion");
        }
    }


    public static Point2PointMessage convertFromProtoBuff(Point2PointMessageOuterClass.Point2PointMessage buffP2pMsg) {
        Point2PointMessage p2pMsg = new Point2PointMessage();
        p2pMsg.setTargetAddressKey(buffP2pMsg.getTargetAddressKey());
        p2pMsg.setMessage(buffP2pMsg.getMessage());
        return p2pMsg;
    }


    public static RpcCmd convertFromProtoBuff(RpcCmdOuterClass.RpcCmd buffRpcCmd) {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setRemoteAddressKey(buffRpcCmd.getRemoteAddressKey());
        rpcCmd.setRandomKey(buffRpcCmd.getRandomKey());
        rpcCmd.setMessage(convertFromProtoBuff(buffRpcCmd.getMessage()));
        return rpcCmd;
    }


    public static Serializable convertFromProtoBuff(BytesDataOuterClass.BytesData bytesData) {
        Serializable serialData = null;
        if (bytesData != null && bytesData.getContent() != null) {
            byte[] bytes = bytesData.getContent().toByteArray();
            serialData = new String(bytes, StandardCharsets.UTF_8);
        }
        return serialData;
    }


    public static MessageDto convertFromProtoBuff(MessageDtoOuterClass.MessageDto buffMessageDto) {
        if (buffMessageDto == null) {
            return null;
        }
        MessageDto messageDto = new MessageDto();
        messageDto.setFromBuff(true);
        messageDto.setAction(buffMessageDto.getAction());
        messageDto.setState(buffMessageDto.getState());
        Any data = buffMessageDto.getData();
        if (data != null) {
            try {
                String typeUrl = data.getTypeUrl();
                if (StringUtils.isNotEmpty(typeUrl)) {
                    messageDto.setSerialData(convertFromProtoBuff((data).unpack(getClzByTypeUrl(typeUrl))));
                }
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return messageDto;
    }

    public static MessageDtoOuterClass.MessageDto parseToProtobuff(MessageDto messageDto) throws IllegalArgumentException {
        if (Objects.isNull(messageDto)) {
            return null;
        }
        MessageDtoOuterClass.MessageDto.Builder builder = MessageDtoOuterClass.MessageDto.newBuilder();
        Optional.ofNullable(messageDto.getAction()).ifPresent(builder::setAction);
        Optional.of(messageDto.getState()).ifPresent(builder::setState);
        Optional.ofNullable(messageDto.getSerialData()).ifPresent(data -> {
            if (data instanceof Message) {
                builder.setData(Any.pack((Message)data));
            } else if (data instanceof Point2PointMessage) {
                builder.setData(Any.pack(Objects.requireNonNull(parseToProtobuff((Point2PointMessage)data))));
            } else if (data instanceof String) {
                String serialData = (String)data;
                BytesDataOuterClass.BytesData.Builder bytesBuilder = BytesDataOuterClass.BytesData.newBuilder();
                bytesBuilder.setContent(ByteString.copyFrom(serialData.getBytes(StandardCharsets.UTF_8)));
                builder.setData(Any.pack(bytesBuilder.build()));
                log.warn("data:{} can not be cast to Message, serialize to byteString", serialData);
            } else {
                throw new IllegalArgumentException("data " + data + " can not be serialize");
            }
        });
        return builder.build();
    }

    public static RpcCmdOuterClass.RpcCmd parseToProtobuff(RpcCmd rpcCmd) {
        if (Objects.isNull(rpcCmd)) {
            return null;
        }
        RpcCmdOuterClass.RpcCmd.Builder builder = RpcCmdOuterClass.RpcCmd.newBuilder();
        Optional.ofNullable(rpcCmd.getMessage()).ifPresent(message -> builder.setMessage(parseToProtobuff(message)));
        Optional.ofNullable(rpcCmd.getRandomKey()).ifPresent(builder::setRandomKey);
        Optional.ofNullable(rpcCmd.getRemoteAddressKey()).ifPresent(builder::setRemoteAddressKey);
        return builder.build();
    }

    public static Point2PointMessageOuterClass.Point2PointMessage parseToProtobuff(Point2PointMessage p2pMsg) {
        if (Objects.isNull(p2pMsg)) {
            return null;
        }
        Point2PointMessageOuterClass.Point2PointMessage.Builder builder = Point2PointMessageOuterClass.Point2PointMessage.newBuilder();
        Optional.ofNullable(p2pMsg.getMessage()).ifPresent(builder::setMessage);
        Optional.ofNullable(p2pMsg.getTargetAddressKey()).ifPresent(builder::setTargetAddressKey);
        return builder.build();
    }

    public static <T extends Message, P extends Serializable> T parseToProtobuff(P param) {
        if (param instanceof RpcCmd) {
            return (T)parseToProtobuff((RpcCmd)param);
        } else if (param instanceof MessageDto) {
            return (T)parseToProtobuff((MessageDto)param);
        } else if (param instanceof Point2PointMessage) {
            return (T)parseToProtobuff((Point2PointMessage)param);
        } else {
            throw new IllegalArgumentException("unsupported data instance");
        }
    }

    private static Class<? extends Message> getClzByTypeUrl(String typeUrl) {
        // 对于更多的泛型需要继续扩展该方法
        if ("type.googleapis.com/Point2PointMessage".equals(typeUrl)) {
            return Point2PointMessageOuterClass.Point2PointMessage.class;
        } else if ("type.googleapis.com/BytesData".equals(typeUrl)) {
            return BytesDataOuterClass.BytesData.class;
        }
        throw new IllegalArgumentException("typeUrl: " + typeUrl + " not found");
    }

}
