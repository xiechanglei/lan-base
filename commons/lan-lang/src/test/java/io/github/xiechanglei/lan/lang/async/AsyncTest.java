package io.github.xiechanglei.lan.lang.async;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AsyncTest {
    /**
     * test case for {@link GlobalAsync#await(Object, long)}
     */
    @Test
    public void testAwait() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                GlobalAsync.put("key", "value");
            } catch (InterruptedException ignored) {
            }
        }) {{
            start();
        }};
        String key = GlobalAsync.await("key", 3000);
        assertEquals("value", key);
    }

    /**
     * test case for {@link GlobalAsync#put(Object, Object)}
     * when call put before await, the await method will throw an exception
     */
    @Test
    public void testPutBefore() {
        GlobalAsync.put("key", "value");
        assertThrows(AwaitTimeoutException.class, () -> GlobalAsync.await("key", 3000));
    }
}
