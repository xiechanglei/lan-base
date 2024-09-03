package io.github.xiechanglei.lan.lang.thread;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池的阻塞的拒绝策略：
 * 当任务无法提交到线程池时，将会阻塞等待，直到任务被提交成功
 *
 * @author xie
 * @date 2024/9/2
 */
public class BlockingRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RejectedExecutionException("Task " + r + " rejected", e);
        }
    }
}