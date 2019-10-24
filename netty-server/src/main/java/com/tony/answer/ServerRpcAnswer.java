package com.tony.answer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
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
public class ServerRpcAnswer implements RpcAnswer, DisposableBean {

    private final RpcClient rpcClient;
    private final ExecutorService executorService;

    public ServerRpcAnswer(RpcClient rpcClient) {
        this.rpcClient = rpcClient;
        int threadNum = Runtime.getRuntime().availableProcessors() * 4;
        executorService = new ThreadPoolExecutor(threadNum, threadNum, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setDaemon(false).setNameFormat("server-ans-%d").build()
        );
    }

    @Override
    public void callback(RpcCmd rpcCmd) {
        executorService.submit(() -> {
            String data = rpcCmd.getMessage().dataOfClazz(String.class);
            int state = rpcCmd.getMessage().getState();
            if (EnumNettyState.REQUEST.getState() == state) {
                log.info("服务端收到客户端请求：「{}」", rpcCmd.getMessage());
                String action = rpcCmd.getMessage().getAction();
                // 执行业务操作，更新message
                // ....
                try {
                    // 模拟服务器端业务逻辑延迟
                    Thread.sleep(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 如果有key则需要响应请求
                if (!StringUtils.isEmpty(rpcCmd.getRandomKey())) {
                    MessageDto message = new MessageDto();
                    message.setAction(action);
                    message.setData("服务端执行请求成功, for:" + data.substring(13));
                    message.setState(EnumNettyState.RESPONSE_OK.getState());
                    rpcCmd.setMessage(message);
                    log.info("服务端执行完毕，发送反馈消息：「{}」", message);
                    rpcClient.send(rpcCmd);
                }
            } else {
                // 响应类信息，不作处理
                log.info("服务端收到客户端响应信息：「{}」", rpcCmd.getMessage());
            }
        });
    }


    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
    }
}
