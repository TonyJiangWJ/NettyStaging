package com.tony;

import com.alibaba.fastjson.JSON;
import com.tony.constants.EnumNettyActions;
import com.tony.message.MessageDto;
import com.tony.serializer.impl.ProtostuffSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

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
        messageDto.setData(simple1);
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
        messageDto.setData(simple1);
        log.info("before serialized: {}", JSON.toJSONString(messageDto));
        byte[] serializedBytes = ProtostuffSerializer.getInstance().serialize(messageDto);
        log.info("after deserialize: {}", JSON.toJSONString(ProtostuffSerializer.getInstance().deSerialize(serializedBytes, MessageDto.class)));
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
