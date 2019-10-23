package com.tony.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangwenjie 2019/10/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfo {
    private String host;
    private int port;
}
