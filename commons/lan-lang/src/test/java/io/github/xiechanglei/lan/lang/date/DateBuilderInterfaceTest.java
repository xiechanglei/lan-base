package io.github.xiechanglei.lan.lang.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateBuilderInterfaceTest {
    /**
     * Test method for {@link DateBuilderInterface#startOfDay(Date)}
     */
    @Test
    public void testStartOfDay() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfDay(date)), "2020-06-06 00:00:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfDay(Date)}
     */
    @Test
    public void testEndOfDay() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfDay(date)), "2020-06-06 23:59:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfMonth(Date)}
     */
    @Test
    public void testStartOfMonth() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfMonth(date)), "2020-06-01 00:00:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfMonth(Date)}
     */
    @Test
    public void testEndOfMonth() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfMonth(date)), "2020-06-30 23:59:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfYear(Date)}
     */
    @Test
    public void testStartOfYear() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfYear(date)), "2020-01-01 00:00:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfYear(Date)}
     */
    @Test
    public void testEndOfYear() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfYear(date)), "2020-12-31 23:59:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfWeek(Date)}
     */
    @Test
    public void testStartOfWeek() {
        Date date1 = DateBuilderInterface.parse("2024-07-18 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfWeek(date1)), "2024-07-15 00:00:00");
        Date date2 = DateBuilderInterface.parse("2024-07-15 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfWeek(date2)), "2024-07-15 00:00:00");
        Date date3 = DateBuilderInterface.parse("2024-07-21 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfWeek(date3)), "2024-07-15 00:00:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfWeek(Date)}
     */
    @Test
    public void testEndOfWeek() {
        Date date1 = DateBuilderInterface.parse("2024-07-18 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfWeek(date1)), "2024-07-21 23:59:59");
        Date date2 = DateBuilderInterface.parse("2024-07-15 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfWeek(date2)), "2024-07-21 23:59:59");
        Date date3 = DateBuilderInterface.parse("2024-07-21 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfWeek(date2)), "2024-07-21 23:59:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfHour(Date)}
     */
    @Test
    public void testStartOfHour() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfHour(date)), "2020-06-06 12:00:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfHour(Date)}
     */
    @Test
    public void testEndOfHour() {
        Date date = DateBuilderInterface.parse("2024-07-18 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfHour(date)), "2024-07-18 12:59:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfMinute(Date)}
     */
    @Test
    public void testStartOfMinute() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfMinute(date)), "2020-06-06 12:11:00");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfMinute(Date)}
     */
    @Test
    public void testEndOfMinute() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfMinute(date)), "2020-06-06 12:11:59");
    }

    /**
     * Test method for {@link DateBuilderInterface#startOfSecond(Date)}
     */
    @Test
    public void testStartOfSecond() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.startOfSecond(date)), "2020-06-06 12:11:33");
    }

    /**
     * Test method for {@link DateBuilderInterface#endOfSecond(Date)}
     */
    @Test
    public void testEndOfSecond() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33.333");
        assertEquals(DateBuilderInterface.format(DateBuilderInterface.endOfSecond(date)), "2020-06-06 12:11:33");
    }

    /**
     * test method for {@link DateBuilderInterface#getDateBuilder(Date)}
     */
    @Test
    public void testGetDateBuilder() {
        Date date = DateBuilderInterface.parse("2020-06-06 12:11:33");
        DateBuilderInterface.DateBuilder builder = DateBuilderInterface.getDateBuilder(date);
        Date res = builder.startToYear().endToMonth().startToDay().endToHour().startToMinute().endToSecond().get();
        assertEquals(DateBuilderInterface.format(res), "2020-06-06 12:11:59");
    }

}
