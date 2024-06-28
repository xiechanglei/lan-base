package io.github.xiechanglei.lan.beans.async;

import org.junit.jupiter.api.Test;

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
