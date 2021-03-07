package com.tony.proto;

import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyProtoType;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.NettyContext;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import com.tony.proto.simple.SampleMessageDto;
import com.tony.proto.simple.SampleRpcCmd;
import com.tony.serializer.impl.ProtobufSerializer;
import com.tony.serializer.impl.ProtostuffSerializer;
import com.tony.serializer.impl.PureProtobufSerializer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author jiangwenjie 2019/10/30
 */
@Slf4j
public class TestProtoDemo {

    private RpcCmd rpcCmd;

    private SampleRpcCmd sampleRpcCmd;

    @Before
    public void init() {
        Point2PointMessage p2pMsg = new Point2PointMessage();
        p2pMsg.setMessage("Hello, this is a p2p message");
        p2pMsg.setTargetAddressKey("/127.0.0.1:12345");

        MessageDto messageDto = new MessageDto();
//        messageDto.setSerialData("string java");
        messageDto.setState(EnumNettyState.REQUEST.getState());
        messageDto.setAction(EnumNettyActions.P2P.getActionKey());
        messageDto.setData("string java");

        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRemoteAddressKey("/127.0.0.1:1234");
//        rpcCmd.setRandomKey("this-is-random-key");
        this.rpcCmd = rpcCmd;
        log.info("origin data:\n{}", rpcCmd);
    }

    @Before
    public void initSample() {
        Point2PointMessage p2pMsg = new Point2PointMessage();
        p2pMsg.setMessage("Hello, this is a p2p message");
        p2pMsg.setTargetAddressKey("/127.0.0.1:12345");

        SampleMessageDto messageDto = new SampleMessageDto();
        messageDto.setData(p2pMsg);
        messageDto.setState(EnumNettyState.REQUEST.getState());
        messageDto.setAction(EnumNettyActions.P2P.getActionKey());

        SampleRpcCmd rpcCmd = new SampleRpcCmd();
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRemoteAddressKey("/127.0.0.1:1234");
        rpcCmd.setRandomKey("this-is-random-key");
        this.sampleRpcCmd = rpcCmd;
        log.info("origin data:\n{}", rpcCmd);
    }

    @Test
    public void testSerializeAndDeserializeStuff() throws Exception {
        File file = new File("stuff-se.dat");
        ProtostuffSerializer.getInstance().serialize(rpcCmd, new FileOutputStream(file));

        RpcCmd rpcCmd = ProtostuffSerializer.getInstance().deSerialize(new FileInputStream(file), RpcCmd.class);
        log.info("after deserialize:\n{}", rpcCmd);
    }

    @Test
    public void testSerializeAndDeserializeStuffBuff() throws Exception {
        File file = new File("stuff-buff-se.dat");
        NettyContext.getInstance().setProtoType(EnumNettyProtoType.STUFF_PROTOBUF.getKey());
        rpcCmd.getMessage().setData("java string stuff-protobuf");
        ProtobufSerializer.getInstance().serialize(rpcCmd, new FileOutputStream(file));
//        file = new File("stuff-buff-se-python.dat");
        RpcCmd rpcCmd = ProtobufSerializer.getInstance().deSerialize(new FileInputStream(file), RpcCmd.class);
        log.info("after deserialize:\n{}", rpcCmd);
        log.info("message:\n{}", rpcCmd.getMessage().dataOfClazz(String.class));
    }


    @Test
    public void testSerializeAndDeserializeStuffBuffSample() throws Exception {
        File file = new File("stuff-buff-sep.dat");
        ProtobufSerializer.getInstance().serialize(sampleRpcCmd, new FileOutputStream(file));
        file = new File("stuff-buff-sep-python.dat");
        SampleRpcCmd rpcCmd = ProtobufSerializer.getInstance().deSerialize(new FileInputStream(file), SampleRpcCmd.class);
        log.info("message content:{}", rpcCmd.getMessage().dataOfClass(Point2PointMessage.class));
        log.info("after deserialize:\n{}", rpcCmd);
    }


    @Test
    public void testSerializeAndDeserializePureBuff() throws Exception {
        File file;
        file = new File("pure-buff-se.dat");
        PureProtobufSerializer.getInstance().serialize(rpcCmd, new FileOutputStream(file));

        file = new File("pure-buff-se.dat");
        RpcCmd rpcCmd = PureProtobufSerializer.getInstance().deSerialize(new FileInputStream(file), RpcCmd.class);
        log.info("after deserialize:\n{}", rpcCmd);
    }


    @Test
    public void testSerialize() throws Exception {
        double totalAmount = 1000000.0;
        double nano = 1.0;
        long start = System.nanoTime(), end;
        long length = 0;
        for (int i = 0; i < totalAmount; i++) {
            byte[] bytes = PureProtobufSerializer.getInstance().serialize(rpcCmd);
            length = bytes.length;
            RpcCmd result = PureProtobufSerializer.getInstance().deSerialize(bytes, RpcCmd.class);
            if (i == 0) {
                log.info("deserialize: {}", result);
            }
        }
        end = System.nanoTime();
        log.info("pure protobuff cost: {}ms bytes length: {}", (end - start) / nano / totalAmount, length);

        start = System.nanoTime();
        for (int i = 0; i < totalAmount; i++) {
            byte[] bytes = ProtostuffSerializer.getInstance().serialize(rpcCmd);
            length = bytes.length;
            RpcCmd result = ProtostuffSerializer.getInstance().deSerialize(bytes, RpcCmd.class);
            if (i == 0) {
                log.info("deserialize: {}", result);
            }
        }
        end = System.nanoTime();
        log.info("protostuff cost: {}ms bytes length: {}", (end - start) / nano / totalAmount, length);

        start = System.nanoTime();
        NettyContext.getInstance().setProtoType(EnumNettyProtoType.STUFF_PROTOBUF.getKey());
        for (int i = 0; i < totalAmount; i++) {
            byte[] bytes = ProtobufSerializer.getInstance().serialize(rpcCmd);
            length = bytes.length;
            RpcCmd result = ProtobufSerializer.getInstance().deSerialize(bytes, RpcCmd.class);
            if (i == 0) {
                log.info("deserialize: {}", result);
            }
        }
        end = System.nanoTime();
        log.info("stuff protobuff cost: {}ms bytes length: {}", (end - start) / nano / totalAmount, length);

    }


}
