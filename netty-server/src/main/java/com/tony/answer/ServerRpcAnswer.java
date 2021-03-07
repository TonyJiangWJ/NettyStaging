package com.tony.answer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.answer.handler.AnswerHandlerFactory;
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
public class ServerRpcAnswer implements RpcAnswer, DisposableBean {

    private final AnswerHandlerFactory handlerFactory;
    private final ExecutorService executorService;

    @Autowired
    public ServerRpcAnswer(AnswerHandlerFactory handlerFactory) {
        this.handlerFactory = handlerFactory;
        int threadNum = Runtime.getRuntime().availableProcessors() * 4;
        executorService = new ThreadPoolExecutor(threadNum, threadNum, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setDaemon(false).setNameFormat("server-ans-%d").build()
        );
    }

    @Override
    public void callback(RpcCmd rpcCmd) {
        executorService.submit(() -> {
            try {
                int state = rpcCmd.getMessage().getState();
                if (EnumNettyState.REQUEST.getState() == state) {
                    log.debug("服务端收到客户端请求：「{}」", rpcCmd.getMessage());
                    // 执行业务操作，更新message等等
                    handlerFactory.getHandler(rpcCmd).handleRequest();
                } else {
                    // 响应类信息，不作处理
                    log.debug("服务端收到客户端响应信息：「{}」", rpcCmd.getMessage());
                    handlerFactory.getHandler(rpcCmd).handleResponse();
                }
            } catch (Exception e) {
                log.error("ServerAnswer执行异常", e);
            }
        });
    }


    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(6, TimeUnit.SECONDS);
    }
}
