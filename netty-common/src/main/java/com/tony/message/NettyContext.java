package com.tony.message;

import com.tony.constants.EnumNettyType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Data
public class NettyContext {
    private EnumNettyType nettyType;

    private static class SingletonHolder {
        final static NettyContext INSTANCE = new NettyContext();
    }

    private NettyContext() {
    }

    public static NettyContext getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
