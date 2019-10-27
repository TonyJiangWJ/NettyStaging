package com.tony.answer;

import com.tony.message.RpcCmd;

/**
 * @author jiangwenjie 2019/10/26
 */
public interface AnswerHandlerService {
    /**
     * 处理业务请求的逻辑
     *
     * @param rpcCmd
     */
    void handleCmdRequest(RpcCmd rpcCmd);

    /**
     * 处理响应消息
     *
     * @param rpcCmd
     */
    default void handleCmdResponse(RpcCmd rpcCmd) {

    }
}
