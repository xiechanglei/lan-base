package io.github.xiechanglei.lan.lang.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ThreadPoolHelper的测试类
 *
 * @author xie
 * @date 2024/9/2
 */
public class ThreadPoolHelperTest {
    @Test
    public void testCreateFixedBlockingVirtualThreadPool() {
        try (ThreadPoolExecutor threadPool = ThreadPoolHelper.createFixedBlockingVirtualThreadPool(10)) {
            threadPool.execute(() -> {
                System.out.println("hello");
            });
            String name = "zhangsan";
            threadPool.execute(() -> {
                System.out.println("hello " + name);
            });
        }
        System.out.println("end");
    }
}
