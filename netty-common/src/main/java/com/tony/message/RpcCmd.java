package com.tony.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author jiangwenjie 2019/10/22
 */
@Data
@Slf4j
public class RpcCmd implements Serializable {
    private MessageDto message;
    private String randomKey;
    private String remoteAddressKey;

    /**
     * 消息持有对象，本地使用 需要标记为transient避免序列化传输
     */
    private volatile transient RpcContent rpcContent;

    /**
     * 创建当前消息流程的随机key和对应的消息对象
     * @return
     */
    public String randomKey() {
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        if (RpcCmdContext.getInstance().hasKey(key)) {
            return randomKey();
        } else {
            rpcContent = RpcCmdContext.getInstance().addKey(key);
        }
        this.randomKey = key;
        return key;
    }

    /**
     * 获取请求结果消息传输对象
     * @return
     */
    public MessageDto loadResult() {
        MessageDto msg = loadRpcContent().getRes();
        if (msg == null) {
            throw new IllegalStateException("request timeout.");
        }
        return msg;
    }

    /**
     * 获取消息体，持有MessageDto
     * @return
     */
    public RpcContent loadRpcContent() {
        if (rpcContent == null) {
            rpcContent = RpcCmdContext.getInstance().getByKey(getRandomKey());
        }
        return rpcContent;
    }

    public void await() {
        await(-1);
    }

    /**
     * 等待消息体释放锁
     * @param timeout
     */
    public void await(long timeout) {
        if (Objects.nonNull(rpcContent.getRes())) {
            return;
        }
        rpcContent.await(timeout);
    }
}
