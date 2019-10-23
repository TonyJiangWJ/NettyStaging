package com.tony.runner;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tony.client.RpcClient;
import com.tony.config.ServerInfo;
import com.tony.constants.EnumNettyActions;
import com.tony.initializer.RpcClientInitializer;
import com.tony.manager.SocketManager;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Service
public class NettyClientRunner implements ApplicationRunner, DisposableBean {

    @Autowired
    private RpcClientInitializer rpcClientInitializer;

    @Autowired
    private RpcClient rpcClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO replace with @Value
        String serverHost = "127.0.0.1";
        int port = 1112;
        rpcClientInitializer.init(new ServerInfo(serverHost, port), true);

        new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("auto-send-%d").build())
                .submit(() -> {
                    while (true) {
                        Thread.sleep(1000);
                        RpcCmd rpcCmd = new RpcCmd();
                        rpcCmd.setRandomKey(rpcCmd.randomKey());

                        rpcCmd.setMessage(generateMessage());
                        List<String> remoteKeys = SocketManager.getInstance().loadAllRemoteKey();
                        if (CollectionUtils.isEmpty(remoteKeys)) {
                            continue;
                        }
                        log.info("客户端发送业务请求信息：「{}」", JSON.toJSONString(rpcCmd.getMessage()));
                        rpcCmd.setRemoteAddressKey(remoteKeys.get(0));
                        MessageDto result = rpcClient.request(rpcCmd);
                        log.info("客户端请求逻辑中获取响应消息：「{}」", JSON.toJSONString(result));
                    }
                });
    }

    private MessageDto generateMessage() {
        MessageDto messageDto = new MessageDto();
        messageDto.setData("Hello，now is " + System.nanoTime());
        messageDto.setAction(EnumNettyActions.SIMPLE_ACTION.getActionKey());
        return messageDto;
    }

    @Override
    public void destroy() throws Exception {
        rpcClientInitializer.destroy();
    }
}
