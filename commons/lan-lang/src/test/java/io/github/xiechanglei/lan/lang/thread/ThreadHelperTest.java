package io.github.xiechanglei.lan.lang.thread;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadHelperTest {

    /**
     * test case for {@link ThreadHelper#call(Runnable)}
     */
    @Test
    public void testCall() throws InterruptedException {
        StringBuilder stringBuilder = new StringBuilder("foo");
        ThreadHelper.callAfter(() -> stringBuilder.append("bar"), 1000);
        assert stringBuilder.toString().equals("foo");
        ThreadHelper.callAfter(() -> assertEquals(stringBuilder.toString(), "foobar"), 3000);
        Thread.sleep(4000);
    }

}
