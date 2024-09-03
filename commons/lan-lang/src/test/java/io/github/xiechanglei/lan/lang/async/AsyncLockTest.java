package io.github.xiechanglei.lan.lang.async;

import io.github.xiechanglei.lan.lang.thread.ThreadHelper;
import org.junit.jupiter.api.Test;

public class AsyncLockTest {
    /**
     * test case for {@link AsyncLock}
     */
    @Test
    public void testLock() {
        AsyncLock lock = new AsyncLock();
        ThreadHelper.callAfter(lock::unlock, 1000);
        lock.lock();
    }
}
