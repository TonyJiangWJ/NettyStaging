package com.tony.message;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 远程传输操作内容上下文
 *
 * @author jiangwenjie 2019/10/22
 */
@Data
public class RpcCmdContext {

    /**
     * 消息对象缓存
     */
    private Map<String, RpcContent> map;
    /**
     * 缓存队列
     */
    private List<RpcContent> cachedList;
    /**
     * 空闲队列，避免经常创建RpcContent
     */
    private final Queue<RpcContent> freeList;

    private int cacheSize = 1024;


    private static class SingletonHolder {
        static final RpcCmdContext INSTANCE = new RpcCmdContext();
    }

    private RpcCmdContext() {
        map = new ConcurrentHashMap<>();
        cachedList = new CopyOnWriteArrayList<>();
        freeList = new LinkedBlockingQueue<>();
    }

    public static RpcCmdContext getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public RpcContent getByKey(String randomKey) {
        RpcContent content = map.get(randomKey);
        if (content != null && cachedList.contains(content)) {
            // 将对象放入空闲队列
            freeList.add(content);
            cachedList.remove(content);
        }
        map.remove(randomKey);
        return content;
    }

    public synchronized boolean hasKey(String randomKey) {
        return map.containsKey(randomKey);
    }

    public synchronized RpcContent addKey(String randomKey) {
        RpcContent content = createRpcContent();
        map.put(randomKey, content);
        return content;
    }

    private RpcContent createRpcContent() {
        if (cachedList.size() < cacheSize) {
            RpcContent content = new RpcContent();
            // 初始化消息对象并放入缓存列表中
            content.init();
            cachedList.add(content);
            return content;
        } else {
            // 当缓存列表已满时，从空闲列表中获取content对象
            return findRpcContent();
        }
    }

    private RpcContent findRpcContent() {
        synchronized (freeList) {
            RpcContent freeContent = freeList.peek();
            if (freeContent != null && freeContent.isUsed()) {
                freeContent.init();
                freeList.poll();
                return freeContent;
            }
        }
        RpcContent content = new RpcContent();
        content.init();
        return content;
    }

}
