package io.github.xiechanglei.lan.lang.thread;

/**
 * 线程工具类
 */
public class ThreadHelper {
    /**
     * 在新线程中执行任务
     *
     * @param runnable 任务
     */
    public static void call(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 在新线程中阻塞指定的时间之后，执行任务
     * @param runnable 任务
     * @param timeout 阻塞时间，单位毫秒
     */
    public static void call(Runnable runnable, long timeout) {
        new Thread(() -> {
            try {
                Thread.sleep(timeout);
                runnable.run();
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

}
