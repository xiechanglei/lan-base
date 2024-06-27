package io.github.xiechanglei.lan.beans.async;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AsyncLock.class)
public class AsyncLockTestCase {
    @Test
    public void test() {
        AsyncLock asyncLock = AsyncLock.create();
        new Thread(() -> {
            try {
                Thread.sleep(100);
                asyncLock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        asyncLock.lock(3000);
    }
}
