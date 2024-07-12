package io.github.xiechanglei.lan.lang.async;

/**
 * 全局异步处理器，用于全局异步消息处理，主要用于给懒人使用，
 * 不需要自己创建异步处理器，直接使用这个就行，前提是你的消息key是全局唯一的。
 *
 * @see AsyncMessageProducer
 */
public class GlobalAsync {
    public static final Async<Object, Object> async = new Async<>();

    /**
     * 阻塞等待异步消息处理完成
     *
     * @param key     消息key
     * @param timeout 超时时间，单位毫秒
     * @param <T>     消息处理结果类型
     * @return 消息处理结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T await(Object key, long timeout) throws AwaitTimeoutException, AsyncKeyExistsException {
        return (T) async.await(key, timeout);
    }

    /**
     * 将消息放入异步处理器
     *
     * @param key      消息key
     * @param response 消息处理结果
     */
    public static void put(Object key, Object response) {
        async.put(key, response);
    }
}
