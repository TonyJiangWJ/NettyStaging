package com.tony.proto.simple;

import lombok.Data;

/**
 * @author jiangwenjie 2019/10/30
 */
@Data
public class SampleRpcCmd {

    private SampleMessageDto message;
    private String randomKey;
    private String remoteAddressKey;

}
