package com.tony.proto;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.tony.proto.pojo.BytesDataOuterClass;
import com.tony.proto.pojo.MessageDtoOuterClass;
import com.tony.proto.pojo.Point2PointMessageOuterClass;
import com.tony.proto.pojo.RpcCmdOuterClass;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangwenjie 2019/11/1
 */
@Slf4j
public class ProtobufSerializeDemo {

    @Test
    public void serializeToFile() throws Exception {
        Point2PointMessageOuterClass.Point2PointMessage.Builder p2pMsgBuilder = Point2PointMessageOuterClass.Point2PointMessage.newBuilder();
        p2pMsgBuilder.setTargetAddressKey("/127.0.0.1:1233");
        p2pMsgBuilder.setMessage("hello from java");

//        BytesDataOuterClass.BytesData.Builder bytesBuilder = BytesDataOuterClass.BytesData.newBuilder();
//        bytesBuilder.setContent(ByteString.copyFrom("bytes data from java".getBytes(StandardCharsets.UTF_8)));


        MessageDtoOuterClass.MessageDto.Builder messageBuilder = MessageDtoOuterClass.MessageDto.newBuilder();
        messageBuilder.setAction("p2p");
        messageBuilder.setState(100);
        messageBuilder.setData(Any.pack(p2pMsgBuilder.build()));
//        messageBuilder.setData(Any.pack(bytesBuilder.build()));


        RpcCmdOuterClass.RpcCmd.Builder builder = RpcCmdOuterClass.RpcCmd.newBuilder();
        builder.setRandomKey("RANDOM_KEY_JAVA");
        builder.setRemoteAddressKey("/127.0.0.1:1234");
        builder.setMessage(messageBuilder.build());

        builder.build().writeTo(new FileOutputStream("java_protobuf.dat"));
    }


    @Test
    public void deserializeFromFile() throws Exception {
        RpcCmdOuterClass.RpcCmd rpcCmd = RpcCmdOuterClass.RpcCmd.parseFrom(new FileInputStream("java_protobuf.dat"));
        Point2PointMessageOuterClass.Point2PointMessage p2pMsg = rpcCmd.getMessage().getData().unpack(Point2PointMessageOuterClass.Point2PointMessage.class);
//        BytesDataOuterClass.BytesData bytesData = rpcCmd.getMessage().getData().unpack(BytesDataOuterClass.BytesData.class);
        log.info("deserialize rpcCmd: \n{}", rpcCmd);
        log.info("deserialize p2pMsg: \n{}", p2pMsg);
//        log.info("deserialize bytesData: \n{}", bytesData);
    }

    @Test
    public void deserializeFromPythonFile() throws Exception {
        RpcCmdOuterClass.RpcCmd rpcCmd = RpcCmdOuterClass.RpcCmd.parseFrom(new FileInputStream("/Users/jiangwenjie/Documents/Repositories/Coding/Tools/src/com/tony/proto/py2/trans-data-pb2.dat"));
//        Point2PointMessageOuterClass.Point2PointMessage p2pMsg = rpcCmd.getMessage().getData().unpack(Point2PointMessageOuterClass.Point2PointMessage.class);
        BytesDataOuterClass.BytesData bytesData = rpcCmd.getMessage().getData().unpack(BytesDataOuterClass.BytesData.class);
        log.info("deserialize rpcCmd: \n{}", rpcCmd);
//        log.info("deserialize p2pMsg: \n{}", p2pMsg);
        log.info("deserialize bytesData: \n{}", bytesData);
    }
}
