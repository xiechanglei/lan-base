package io.github.xiechanglei.lan.lang.thread;

import java.util.concurrent.*;

/**
 * 线程工具类
 */
public class ThreadPoolHelper {
    /**
     * 创建一个定长的线程池，当线程池中的线程数量达到最大值时，新提交的任务将会被阻塞，没有任务队列
     * 需要注意的是，这里的线程池大小是固定的，不会动态调整，
     * corePoolSize和maximumPoolSize相等，所以不会有线程回收的情况，适合短时间内大量任务的场景，如爬虫，用完即销毁
     *
     * @param pollSize 线程池大小
     * @return 线程池
     */
    public static ThreadPoolExecutor createFixedBlockingThreadPool(int pollSize) {
        return createThreadPoolWithNoQueue(pollSize, pollSize, Executors.defaultThreadFactory());
    }

    /**
     * 创建一个定长的线程池，当线程池中的线程数量达到最大值时，新提交的任务将会被阻塞，没有任务队列
     *
     * @param corePoolSize    线程池核心线程数
     * @param maximumPoolSize 线程池最大线程数
     * @return 线程池
     */
    public static ThreadPoolExecutor createFixedBlockingThreadPool(int corePoolSize, int maximumPoolSize) {
        return createThreadPoolWithNoQueue(corePoolSize, maximumPoolSize, Executors.defaultThreadFactory());
    }


    /**
     * 创建一个定长的虚拟线程池，当线程池中的线程数量达到最大值时，新提交的任务将会被阻塞，没任务队列
     */
    public static ThreadPoolExecutor createFixedBlockingVirtualThreadPool(int pollSize) {
        return createThreadPoolWithNoQueue(pollSize, pollSize, Thread.ofVirtual().factory());
    }

    /**
     * 创建一个定长的虚拟线程池，当线程池中的线程数量达到最大值时，新提交的任务将会被阻塞，没任务队列
     * 需要注意的是，这里的线程池大小是固定的，不会动态调整，
     * corePoolSize和maximumPoolSize相等，所以不会有线程回收的情况，适合短时间内大量任务的场景，如爬虫，用完即销毁
     */
    public static ThreadPoolExecutor createFixedBlockingVirtualThreadPool(int corePoolSize, int maximumPoolSize) {
        return createThreadPoolWithNoQueue(corePoolSize, maximumPoolSize, Thread.ofVirtual().factory());
    }


    private static ThreadPoolExecutor createThreadPoolWithNoQueue(int corePoolSize, int maximumPoolSize, ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(
                corePoolSize, //corePoolSize
                maximumPoolSize, //maximumPoolSize
                corePoolSize == maximumPoolSize ? 0 : 3000L,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(),
                threadFactory,
                new BlockingRejectedExecutionHandler()
        );
    }
}
