package com.tony;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.Any;
import com.tony.constants.EnumNettyActions;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import com.tony.message.protobuff.MessageDtoOuterClass;
import com.tony.message.protobuff.Point2PointMessageOuterClass;
import com.tony.message.protobuff.RpcCmdOuterClass;
import com.tony.message.protobuff.util.PojoConverterUtil;
import com.tony.serializer.impl.ProtostuffSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;

/**
 * @author jiangwenjie 2019/10/22
 */
@Slf4j
public class SimpleTest {


    @Test
    public void testMessageDto() {
        MessageDto messageDto = new MessageDto();
        Simple1 simple1 = new Simple1("simple 1");
        messageDto.setAction(EnumNettyActions.NEW_CONNECT.getActionKey());
        messageDto.setSerialData(simple1);
        log.info("simple1 data: {}", JSON.toJSONString(messageDto.dataOfClazz(Simple1.class)));
        try {
            log.info("can not get simple2 data: {}", JSON.toJSONString(messageDto.dataOfClazz(Simple2.class)));
            Assert.fail();
        } catch (IllegalArgumentException e) {
            log.info("catch instance error exception ", e);
        }
    }

    @Test
    public void testSerializer() {
        MessageDto messageDto = new MessageDto();
        messageDto.setAction(EnumNettyActions.NEW_CONNECT.getActionKey());
        Simple1 simple1 = new Simple1("sample1");
        messageDto.setSerialData(simple1);
        log.info("before serialized: {}", JSON.toJSONString(messageDto));
        byte[] serializedBytes = ProtostuffSerializer.getInstance().serialize(messageDto);
        log.info("after deserialize: {}", JSON.toJSONString(ProtostuffSerializer.getInstance().deSerialize(serializedBytes, MessageDto.class)));
    }

    @Test
    public void testRpcCmdMsg() throws Exception {
        MessageDto messageDto = new MessageDto();
        messageDto.setAction("auth");
        messageDto.setState(200);

        messageDto.setSerialData("d9586257fb254d279165c852611e6fc5");
        RpcCmd sampleRpcCmd = new RpcCmd();
        sampleRpcCmd.setMessage(messageDto);
        sampleRpcCmd.setRandomKey("d2d9327adcdd44139e0d604a9561");
        byte[] bytes = ProtostuffSerializer.getInstance().serialize(sampleRpcCmd);
        File file = new File("data2.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @Test
    public void testDeserializeCmd() throws Exception {
        File file = new File("data2.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        RpcCmd rpcCmd = ProtostuffSerializer.getInstance().deSerialize(fileInputStream, RpcCmd.class);
        log.info("deserialize cmd: {}", JSON.toJSONString(rpcCmd));
    }

    @Test
    public void testSerializeCmd() throws Exception {
        Point2PointMessage point2PointMessage = new Point2PointMessage();
        point2PointMessage.setMessage("Hello, 56865");
        point2PointMessage.setTargetAddressKey("/127.0.0.1:56865");

        MessageDto messageDto = new MessageDto();
        messageDto.setAction("p2p");
        messageDto.setState(200);
        messageDto.setSerialData(point2PointMessage);
        RpcCmd sampleRpcCmd = new RpcCmd();
        sampleRpcCmd.setMessage(messageDto);
        sampleRpcCmd.setRandomKey("d2d9327adcdd44139e0d604a9561");
        byte[] bytes = ProtostuffSerializer.getInstance().serialize(sampleRpcCmd);
        File file = new File("data1.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @Test
    public void testSerializeByProtoBuff() throws Exception {
        Point2PointMessageOuterClass.Point2PointMessage.Builder p2pBuilder = Point2PointMessageOuterClass.Point2PointMessage.newBuilder();

        p2pBuilder.setMessage("Hello, 56865");
        p2pBuilder.setTargetAddressKey("/127.0.0.1:56865");

        MessageDtoOuterClass.MessageDto.Builder messageDtoBuilder = MessageDtoOuterClass.MessageDto.newBuilder();
        messageDtoBuilder.setAction("p2p");
        messageDtoBuilder.setState(200);
        messageDtoBuilder.setData(Any.pack(p2pBuilder.build()));
        MessageDtoOuterClass.MessageDto messageDto = messageDtoBuilder.build();
        messageDto.writeTo(new FileOutputStream("data3.txt"));
    }

    @Test
    public void testDeserializeByProtoBuff() throws Exception {
        File file = new File("data4.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        MessageDtoOuterClass.MessageDto messageDto = MessageDtoOuterClass.MessageDto.parseFrom(fileInputStream);

        log.info("deserialize:{} {}", messageDto.getAction(), messageDto.getState());
    }

    @Test
    public void testAutoSerializeProtoBuff() throws Exception {
        Point2PointMessage point2PointMessage = new Point2PointMessage();
        point2PointMessage.setMessage("Hello, 56865, from Java");
        point2PointMessage.setTargetAddressKey("/127.0.0.1:56865");

        MessageDto messageDto = new MessageDto();
        messageDto.setAction("p2p");
        messageDto.setState(200);
        messageDto.setSerialData(point2PointMessage);
        RpcCmd sampleRpcCmd = new RpcCmd();
        sampleRpcCmd.setMessage(messageDto);
        sampleRpcCmd.setRandomKey("d2d9327adcdd44139e0d604a9561");
        sampleRpcCmd.setRemoteAddressKey("1231231");
        RpcCmdOuterClass.RpcCmd rpcCmdBuff = PojoConverterUtil.parseToProtobuff(sampleRpcCmd);

        File file = new File("data4.txt");
        FileOutputStream outputStream = new FileOutputStream(file);
        rpcCmdBuff.writeTo(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    @Test
    public void testAutoDeserializeProtoBuff() throws Exception {
        File file = new File("data14.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        RpcCmdOuterClass.RpcCmd rpcCmd = RpcCmdOuterClass.RpcCmd.parseFrom(fileInputStream);
        RpcCmd stuffCmd = PojoConverterUtil.convertFromProtoBuff(rpcCmd);
        log.info("cmd:{}", stuffCmd);
        Point2PointMessage p2pMessage = stuffCmd.getMessage().dataOfClazz(Point2PointMessage.class);
        log.info("deserialize:{}", JSON.toJSONString(p2pMessage));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Simple1 implements Serializable {
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Simple2 implements Serializable {
        private String name;
    }


}
