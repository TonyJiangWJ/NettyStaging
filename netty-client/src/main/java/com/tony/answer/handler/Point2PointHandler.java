package com.tony.answer.handler;

import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangwenjie 2021/2/22
 */
@Slf4j
public class Point2PointHandler extends ClientAnswerHandlerAdapter {
    public Point2PointHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    @Override
    protected void doHandleRequest() {
        Point2PointMessage point2PointMessage = rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class);
        log.info("客户端收到来自：「{}」的点对点消息:{}", point2PointMessage.getTargetAddressKey(), point2PointMessage.getMessage());
    }
}
