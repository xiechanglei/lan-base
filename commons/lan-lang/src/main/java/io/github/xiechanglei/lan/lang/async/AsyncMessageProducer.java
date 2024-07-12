package io.github.xiechanglei.lan.lang.async;

/**
 * 抽象异步消息处理器的接口
 *
 * @param <K> 消息key的类型
 * @param <T> 消息返回的类型
 */
public interface AsyncMessageProducer<K, T> {

    /**
     * 同步阻塞当前线程等待消息返回，如果超时则抛出异常
     *
     * @param key     消息key
     * @param timeout 等待超时时间，单位毫秒
     * @return 消息返回内容
     */
    T await(K key, long timeout);

    /**
     * 将消息放入消息处理机，await方法会释放阻塞，获取到消息
     *
     * @param key      消息key
     * @param response 消息返回内容
     */
    void put(K key, T response);
}
