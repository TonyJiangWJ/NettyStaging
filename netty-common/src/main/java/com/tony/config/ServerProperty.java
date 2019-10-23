package com.tony.config;

import lombok.Data;

/**
 * @author jiangwenjie 2019/10/23
 */
@Data
public class ServerProperty {
    private String listenHost;
    private int listenPort;
    private long checkTime;

}
