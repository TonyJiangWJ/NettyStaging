package com.tony.answer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    private final ExecutorService executorService;
    private RpcClient rpcClient;

    @Autowired
    public ClientRpcAnswer(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
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
            int state = rpcCmd.getMessage().getState();
            if (EnumNettyState.REQUEST.getState() == state) {
                log.info("客户端获取请求信息：「{}」", rpcCmd.getMessage());
                // 如果是请求类消息，执行业务请求
                String action = rpcCmd.getMessage().getAction();
                // 执行业务操作，更新message
                // ....
                // 如果有key则需要响应请求
                if (!StringUtils.isEmpty(rpcCmd.getRandomKey())) {
                    MessageDto message = new MessageDto();
                    message.setAction(action);
                    message.setData("客户端执行请求成功");
                    message.setState(EnumNettyState.RESPONSE_OK.getState());
                    rpcCmd.setMessage(message);
                    rpcClient.send(rpcCmd);
                }
            } else {
                // 非请求类的可以不作处理
                log.info("客户端获取业务响应信息：「{}」", rpcCmd.getMessage());
            }

        });
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
        executorService.awaitTermination(6, TimeUnit.SECONDS);
    }
}
