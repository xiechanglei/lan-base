package io.github.xiechanglei.lan.lang.async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步处理器，主要是用于异步处理消息，等待消息返回。
 * 一些场景下，我们可能在多个线程中处理消息的发送与接收，如何去处理这些消息的关联关系，通常是一个比较复杂的过程，
 * 这里的设计是定义全局的消息处理机，发送消息与接收消息只负责与消息处理机的交互，不需要处理互相通信的逻辑。
 * @param <K> 消息key的类型
 * @param <T> 消息返回的类型
 */
public class Async<K, T> implements AsyncMessageProducer<K, T> {
    private final Map<K, AsyncLock> keyMap = new ConcurrentHashMap<>();
    private final Map<K, T> responseMap = new ConcurrentHashMap<>();

    /**
     * 等待消息返回
     * @param key 消息key，用于关联一部分返回的消息
     * @param timeout 等待超时的时间
     * @return 异步返回的消息
     * @throws Exception 当等待消息超时的时候抛出异常
     */
    public T await(K key, long timeout) throws Exception {
        if (keyMap.containsKey(key)) {
            throw new Exception("key exists!");
        }
        AsyncLock lock = AsyncLock.create();
        keyMap.put(key, lock);
        lock.lock(timeout);
        if (keyMap.containsKey(key)) {
            keyMap.remove(key);
            throw AwaitTimeoutException.INSTANCE;
        } else {
            return responseMap.remove(key);
        }
    }

    /**
     * 将消息放入消息处理机，await方法会施放阻塞，获取到消息
     * @param key 消息key
     * @param response 消息返回内容
     */
    public void put(K key, T response) {
        if (keyMap.containsKey(key)) {
            responseMap.put(key, response);
            keyMap.remove(key).unlock();
        }
    }

}