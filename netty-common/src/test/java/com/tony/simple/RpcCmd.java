package com.tony.simple;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author jiangwenjie 2019/10/22
 */
@Data
@Slf4j
public class RpcCmd implements Serializable {
    private MessageDto message;
    private String randomKey;
    /**
     * 目标地址，不需要序列化传输
     */
    private transient String remoteAddressKey;
}
