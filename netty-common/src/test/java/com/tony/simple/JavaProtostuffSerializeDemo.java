package com.tony.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author jiangwenjie 2019/11/1
 */
@Slf4j
public class JavaProtostuffSerializeDemo {

    @Test
    public void serializeToFile() throws Exception {
        Point2PointMessage p2pMsg = new Point2PointMessage();
        p2pMsg.setTargetAddressKey("/127.0.0.1:1233");
        p2pMsg.setMessage("url=https://kh.10jqka.com.cn/static/862.html^webid=2804");
        MessageDto messageDto = new MessageDto();
        messageDto.setAction("p2p");
        messageDto.setState(100);
        messageDto.setData(p2pMsg, false);

        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setMessage(messageDto);
        rpcCmd.setRandomKey("RANDOM_KEY_JAVA");
        rpcCmd.setRemoteAddressKey("/127.0.0.1:1234");
        ProtobufSerializer.getInstance().serialize(rpcCmd, new FileOutputStream("java_proto_simple.dat"));
    }

    @Test
    public void deserializeFromFile() throws Exception {
        RpcCmd rpcCmd = ProtobufSerializer.getInstance().deSerialize(new FileInputStream("java_proto_simple.dat"), RpcCmd.class);
        log.info("deserialize cmd:\n{}", rpcCmd);
        log.info("deserialize p2p msg:\n{}", rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class, false));
    }

    @Test
    public void deserializeFromPythonFile() throws Exception {
        RpcCmd rpcCmd = ProtobufSerializer.getInstance().deSerialize(new FileInputStream("/Users/jiangwenjie/Documents/Repositories/Coding/Tools/src/com/tony/proto/py3/trans-data.dat"), RpcCmd.class);
        log.info("deserialize cmd:\n{}", rpcCmd);
        log.info("deserialize p2p msg:\n{}", rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class, false));
    }
}
