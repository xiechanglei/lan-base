package io.github.xiechanglei.lan.base.beans.async;

public class GlobalAsync {
    public static final Async<Object, Object> async = new Async<>();

    @SuppressWarnings("unchecked")
    public static <T> T await(Object key, long timeout) throws Exception {
        return (T) async.await(key, timeout);
    }

    public static void put(Object key, Object response) {
        async.put(key, response);
    }
}
