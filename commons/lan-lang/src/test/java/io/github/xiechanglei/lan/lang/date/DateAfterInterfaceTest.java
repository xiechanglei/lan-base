package io.github.xiechanglei.lan.lang.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateAfterInterfaceTest {

    /**
     * Test method for {@link DateAfterInterface#afterDays(Date, int)}
     */
    @Test
    public void testAfterDays() {
        Date date1 = DateAfterInterface.afterDays(DateAfterInterface.parse("2020-12-31 00:00:00"), 1);
        assertEquals(DateAfterInterface.parse("2021-01-01 00:00:00").getTime(), date1.getTime());
        Date date2 = DateAfterInterface.afterDays(DateAfterInterface.parse("2020-12-31 00:00:00"), -1);
        assertEquals(DateAfterInterface.parse("2020-12-30 00:00:00").getTime(), date2.getTime());
        Date date3 = DateAfterInterface.afterDays(DateAfterInterface.parse("2020-01-01 00:00:03"), -1);
        assertEquals(DateAfterInterface.parse("2019-12-31 00:00:03").getTime(), date3.getTime());
    }

    /**
     * Test method for {@link DateAfterInterface#afterHours(Date, int)}
     */
    @Test
    public void testAfterHours() {
        Date date1 = DateAfterInterface.afterHours(DateAfterInterface.parse("2020-12-31 00:00:00"), 1);
        assertEquals(DateAfterInterface.parse("2020-12-31 01:00:00").getTime(), date1.getTime());
        Date date2 = DateAfterInterface.afterHours(DateAfterInterface.parse("2020-12-31 00:00:00"), -1);
        assertEquals(DateAfterInterface.parse("2020-12-30 23:00:00").getTime(), date2.getTime());
        Date date3 = DateAfterInterface.afterHours(DateAfterInterface.parse("2020-01-01 00:00:03"), -1);
        assertEquals(DateAfterInterface.parse("2019-12-31 23:00:03").getTime(), date3.getTime());
    }

    /**
     * Test method for {@link DateAfterInterface#afterMinutes(Date, int)}
     */
    @Test
    public void testAfterMinutes() {
        Date date1 = DateAfterInterface.afterMinutes(DateAfterInterface.parse("2020-12-31 00:00:00"), 1);
        assertEquals(DateAfterInterface.parse("2020-12-31 00:01:00").getTime(), date1.getTime());
        Date date2 = DateAfterInterface.afterMinutes(DateAfterInterface.parse("2020-12-31 00:00:00"), -1);
        assertEquals(DateAfterInterface.parse("2020-12-30 23:59:00").getTime(), date2.getTime());
        Date date3 = DateAfterInterface.afterMinutes(DateAfterInterface.parse("2020-01-01 00:00:03"), -1);
        assertEquals(DateAfterInterface.parse("2019-12-31 23:59:03").getTime(), date3.getTime());
    }

    /**
     * Test method for {@link DateAfterInterface#afterSeconds(Date, int)}
     */
    @Test
    public void testAfterSeconds() {
        Date date1 = DateAfterInterface.afterSeconds(DateAfterInterface.parse("2020-12-31 00:00:00"), 1);
        assertEquals(DateAfterInterface.parse("2020-12-31 00:00:01").getTime(), date1.getTime());
        Date date2 = DateAfterInterface.afterSeconds(DateAfterInterface.parse("2020-12-31 00:00:00"), -1);
        assertEquals(DateAfterInterface.parse("2020-12-30 23:59:59").getTime(), date2.getTime());
    }

    /**
     * Test method for {@link DateAfterInterface#afterMilliseconds(Date, int)}
     */
    @Test
    public void testAfterMilliseconds() {
        Date date1 = DateAfterInterface.afterMilliseconds(DateAfterInterface.parse("2020-12-31 00:00:00"), 1);
        assertEquals(DateAfterInterface.parse("2020-12-31 00:00:00.001").getTime(), date1.getTime());
        Date date2 = DateAfterInterface.afterMilliseconds(DateAfterInterface.parse("2020-12-31 00:00:00"), -1);
        assertEquals(DateAfterInterface.parse("2020-12-30 23:59:59.999").getTime(), date2.getTime());
    }
}
