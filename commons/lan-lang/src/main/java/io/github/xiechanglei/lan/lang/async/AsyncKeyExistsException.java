package io.github.xiechanglei.lan.lang.async;

/**
 * 异步消息key已经存在异常
 */
public class AsyncKeyExistsException extends RuntimeException {
    public static final AsyncKeyExistsException INSTANCE = new AsyncKeyExistsException();
}
