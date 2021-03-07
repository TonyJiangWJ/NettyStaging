package com.tony.answer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.answer.handler.ClientAnswerHandlerFactory;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyState;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
public class ClientRpcAnswer implements RpcAnswer, DisposableBean {

    private final ClientAnswerHandlerFactory handlerFactory;
    private final ExecutorService executorService;

    @Autowired
    public ClientRpcAnswer(ClientAnswerHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        int threadsNum = Runtime.getRuntime().availableProcessors() * 5;
        this.executorService = new ThreadPoolExecutor(threadsNum, threadsNum,
                0, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setDaemon(false).setNameFormat("rpc-client-answer-%d").build()
        );
    }


    @Override
    public void callback(RpcCmd rpcCmd) {
        executorService.submit(() -> {
            try {
                int state = rpcCmd.getMessage().getState();
                if (EnumNettyState.REQUEST.getState() == state) {
                    log.debug("客户端获取请求信息：「{}」", rpcCmd.getMessage());
                    handlerFactory.getHandler(rpcCmd).handleRequest();
                } else {
                    // 非请求类的可以不作处理
                    handlerFactory.getHandler(rpcCmd).handleResponse();
                }
            } catch (Exception e) {
                log.error("ClientAnswer执行异常", e);
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(6, TimeUnit.SECONDS);
    }
}
