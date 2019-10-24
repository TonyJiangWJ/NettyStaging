package com.tony.runner;
import	java.util.Scanner;

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
import org.springframework.util.StringUtils;

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

    @Autowired
    private ServerInfo serverInfo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        rpcClientInitializer.init(serverInfo, true);

        new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("auto-send-%d").build())
                .submit(() -> {
                    Scanner scanner = new Scanner(System.in);
                    while(scanner.hasNext()) {
                        String readMessage = scanner.next();
                        RpcCmd rpcCmd = new RpcCmd();
                        rpcCmd.setRandomKey(rpcCmd.randomKey());

                        rpcCmd.setMessage(generateMessage(readMessage));
                        List<String> remoteKeys = SocketManager.getInstance().loadAllRemoteKey();
                        if (CollectionUtils.isEmpty(remoteKeys)) {
                            continue;
                        }
                        log.info("客户端发送业务请求信息：「{}」", JSON.toJSONString(rpcCmd.getMessage()));
                        rpcCmd.setRemoteAddressKey(remoteKeys.get(0));
                        try {
                            MessageDto result = rpcClient.request(rpcCmd, 5);
                            log.info("客户端请求逻辑中获取响应消息：「{}」", JSON.toJSONString(result));
                        } catch (Exception e) {
                            log.error("获取请求结果失败", e);
                        }
                    }

                });
    }

    private MessageDto generateMessage(String messageContent) {
        MessageDto messageDto = new MessageDto();
        messageDto.setData("Hello，now is " + (StringUtils.isEmpty(messageContent) ? System.nanoTime() : messageContent));
        messageDto.setAction(EnumNettyActions.SIMPLE_ACTION.getActionKey());
        return messageDto;
    }

    @Override
    public void destroy() throws Exception {
        rpcClientInitializer.destroy();
    }
}
