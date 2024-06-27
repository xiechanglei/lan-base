package io.github.xiechanglei.lan.beans.async;

public class AwaitTimeoutException extends  RuntimeException{
    public static final AwaitTimeoutException INSTANCE = new AwaitTimeoutException();
}
