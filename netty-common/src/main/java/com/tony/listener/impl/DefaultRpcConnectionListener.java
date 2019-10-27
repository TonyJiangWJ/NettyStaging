package com.tony.listener.impl;

import com.tony.listener.RpcConnectionListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
public class DefaultRpcConnectionListener implements RpcConnectionListener {
    @Override
    public void connect(String remoteKey) {
        log.info("connected to: " + remoteKey);
    }

    @Override
    public void disconnect(String remoteKey) {
        log.info("disconnected from: " + remoteKey);
    }
}
