package io.github.xiechanglei.lan.lang.async;

/**
 * 异步消息处理超时异常
 */
public class AwaitTimeoutException extends RuntimeException {
    public static final AwaitTimeoutException INSTANCE = new AwaitTimeoutException();
}
