package io.github.xiechanglei.lan.lang.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 异步锁，用于同步等待，异步唤醒
 */
public class AsyncLock {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    /**
     * 阻塞线程，直到被唤醒
     */
    public void lock() {
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException ignored) {
        }
        lock.unlock();
    }

    /**
     * 阻塞线程，直到被唤醒或者超时
     * @param waitTime 等待时间，单位毫秒
     * @return 是否是正常唤醒
     */
    public boolean lock(long waitTime) {
        lock.lock();
        try {
            return condition.await(waitTime, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ignored) {
        }
        lock.unlock();
        return false;
    }

    public void unlock() {
        lock.lock();
        condition.signal();
        lock.unlock();
    }

    public static AsyncLock create() {
        return new AsyncLock();
    }

}