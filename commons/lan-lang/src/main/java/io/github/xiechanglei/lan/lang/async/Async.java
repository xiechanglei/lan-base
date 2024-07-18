package io.github.xiechanglei.lan.lang.async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步处理器，主要是用于异步处理消息，等待消息返回。
 * 一些场景下，我们可能在多个线程中处理消息的发送与接收，如何去处理这些消息的关联关系，通常是一个比较复杂的过程，
 * 这里的设计是定义全局的消息处理机，发送消息与接收消息只负责与消息处理机的交互，不需要处理互相通信的逻辑。
 *
 * @param <K> 消息key的类型
 * @param <T> 消息返回的类型
 */
public class Async<K, T> implements AsyncMessageProducer<K, T> {
    private final Map<K, AsyncLock> lockMap = new ConcurrentHashMap<>();
    private final Map<K, T> responseMap = new ConcurrentHashMap<>();

    /**
     * 等待消息返回
     *
     * @param key     消息key，用于关联一部分返回的消息
     * @param timeout 等待超时的时间
     * @return 异步返回的消息
     * @throws AwaitTimeoutException   等待超时异常
     * @throws AsyncKeyExistsException 异步key已经存在异常
     */
    public T await(K key, long timeout) throws AwaitTimeoutException, AsyncKeyExistsException {
        if (lockMap.containsKey(key)) {
            throw AsyncKeyExistsException.INSTANCE;
        }
        AsyncLock lock = AsyncLock.create();
        lockMap.put(key, lock);
        boolean success = lock.lock(timeout);
        lockMap.remove(key);
        T result = responseMap.remove(key);
        if (!success) {
            throw AwaitTimeoutException.INSTANCE;
        }
        return result;
    }

    /**
     * 将消息放入消息处理机，await方法会施放阻塞，获取到消息
     * 只有存在key的时候才会放入消息，同时唤醒等待的线程
     *
     * @param key      消息key
     * @param response 消息返回内容
     */
    public void put(K key, T response) {
        if (lockMap.containsKey(key)) {
            responseMap.put(key, response);
            lockMap.remove(key).unlock();
        }
    }

}