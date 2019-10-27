package com.tony.message.data;

import lombok.Data;

import java.io.Serializable;

/**
 * 点对点通信data对象
 *
 * @author jiangwenjie 2019/10/26
 */
@Data
public class Point2PointMessage implements Serializable {
    private String targetAddressKey;
    private String message;
}
