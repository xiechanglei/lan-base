package io.github.xiechanglei.lan.base.beans.async;

public class AwaitTimeoutException extends  RuntimeException{
    public static final AwaitTimeoutException INSTANCE = new AwaitTimeoutException();
}
