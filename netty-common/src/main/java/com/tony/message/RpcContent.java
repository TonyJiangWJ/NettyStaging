package com.tony.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangwenjie 2019/10/22
 */
@Slf4j
@Data
public class RpcContent implements Serializable {

    private volatile  MessageDto res;
    private Lock lock;
    private Condition condition;
    private boolean isUsed;

    public RpcContent() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void init() {
        res = null;
        isUsed = false;
    }

    public void clear() {
        res = null;
        isUsed = true;
    }

    /**
     * 等待操作完成，无超时时间，一直等待
     */
    public void await() {
        try {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 等待操作完成，超时时间timeout 单位秒
     * @param timeout 等待超时时间
     */
    public void await(long timeout) {
        if (timeout < 1) {
            await();
            return;
        }
        try {
            lock.unlock();
            try {
                condition.await(timeout, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 发送完成信号
     */
    public void signal() {
        try {
            lock.lock();
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取请求结果内容
     * @return MessageDto 消息传递对象
     */
    public MessageDto getRes() {
        synchronized (this) {
            return res;
        }
    }

    /**
     * 设置请求结果内容
     * @param res  MessageDto 消息传递对象
     */
    public void setRes(MessageDto res) {
        synchronized (this) {
            this.res = res;
        }
    }
}
