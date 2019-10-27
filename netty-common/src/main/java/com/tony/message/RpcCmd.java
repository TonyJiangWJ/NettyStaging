package com.tony.message;

import com.tony.RpcException;
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
     *
     * @return
     */
    public String randomKey() {
        // 可以更改随机数创建方法，比如雪花算法等 避免分布式随机值相同以及提升性能
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        if (RpcCmdContext.getInstance().hasKey(key)) {
            return randomKey();
        } else {
            // 将消息持有对象放入到本地缓存
            rpcContent = RpcCmdContext.getInstance().addKey(key);
        }
        this.randomKey = key;
        return key;
    }

    /**
     * 创建随机key，但是不需要进行等待反馈值
     *
     * @return
     */
    public String emptyKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获取请求结果消息传输对象
     *
     * @return
     */
    public MessageDto loadResult() throws RpcException {
        MessageDto msg = loadRpcContent().getRes();
        if (msg == null) {
            throw new RpcException("request timeout.");
        }
        return msg;
    }

    /**
     * 获取消息体，持有MessageDto
     *
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
     *
     * @param timeout 单位秒
     */
    public void await(long timeout) {
        if (Objects.nonNull(rpcContent.getRes())) {
            return;
        }
        rpcContent.await(timeout);
    }
}
