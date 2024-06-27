package io.github.xiechanglei.lan.base.beans.async;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AsyncLock {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    public void lock() {
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException ignored) {
        }
        lock.unlock();
    }

    /**
     * @return is timout
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