package io.github.xiechanglei.lan.beans.async;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsyncTestCase {
    @Test
    public void test() throws Exception {
        Async<String, String> async = new Async<>();
        new Thread(() -> {
            try {
                Thread.sleep(100);
                async.put("key", "value");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        String key = async.await("key", 3000);
        assertEquals("value", key);
    }
}
