package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;
import org.apache.commons.lang3.StringUtils;

/**
 * @author jiangwenjie 2021/2/22
 */
public abstract class AbstractAnswerHandler {
    protected RpcClient rpcClient;
    protected RpcCmd rpcCmd;

    public AbstractAnswerHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        this.rpcClient = rpcClient;
        this.rpcCmd = rpcCmd;
    }

    public void handleRequest() {
        // 如果有key则需要响应请求
        if (StringUtils.isNotEmpty(rpcCmd.getRandomKey())) {
            doHandleRequest();
        }
    }

    /**
     * 处理answer请求
     */
    protected abstract void doHandleRequest();

    /**
     * 处理响应内容
     */
    public abstract void handleResponse();
}
