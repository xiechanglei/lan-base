package io.github.xiechanglei.lan.lang.date;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateCountInterfaceTest {
    /**
     * Test method for {@link DateCountInterface#countNatureYears(Date, Date)}
     */
    @Test
    public void testCountNatureYears() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2021-01-01 12:12:12");
        assertEquals(DateCountInterface.countNatureYears(date1, date2), 1);
        assertEquals(DateCountInterface.countNatureYears(date2, date1), -1);
    }

    /**
     * Test method for {@link DateCountInterface#getYearsBetween(Date, Date)}
     */
    @Test
    public void testGetYearsBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2021-02-01 12:12:12");
        List<Date> yearsBetween = DateCountInterface.getYearsBetween(date1, date2);
        assertEquals(yearsBetween.size(), 2);
        assertEquals(yearsBetween.get(0), DateCountInterface.parse("2020-01-01 00:00:00"));
        assertEquals(yearsBetween.get(1), DateCountInterface.parse("2021-01-01 00:00:00"));
    }

    /**
     * Test method for {@link DateCountInterface#countNatureMonths(Date, Date)}
     */
    @Test
    public void testCountNatureMonths() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2021-02-01 12:12:12");
        assertEquals(DateCountInterface.countNatureMonths(date1, date2), 2);
        assertEquals(DateCountInterface.countNatureMonths(date2, date1), -2);
    }
    
    /**
     * Test method for {@link DateCountInterface#getMonthsBetween(Date, Date)}
     */
    @Test
    public void testGetMonthsBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2021-02-01 12:12:12");
        List<Date> monthsBetween = DateCountInterface.getMonthsBetween(date1, date2);
        assertEquals(monthsBetween.size(), 3);
        assertEquals(monthsBetween.get(0), DateCountInterface.parse("2020-12-01 00:00:00"));
        assertEquals(monthsBetween.get(1), DateCountInterface.parse("2021-01-01 00:00:00"));
        assertEquals(monthsBetween.get(2), DateCountInterface.parse("2021-02-01 00:00:00"));
    }

    /**
     * Test method for {@link DateCountInterface#countNatureDays(Date, Date)} (Date, Date)}
     */
    @Test
    public void testCountNatureDays() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2021-01-01 12:12:12");
        assertEquals(DateCountInterface.countNatureDays(date1, date2), 1);
        assertEquals(DateCountInterface.countNatureDays(date2, date1), -1);
    }

    /**
     * Test method for {@link DateCountInterface#countNatureHours(Date, Date)} (Date, Date)}
     */
    @Test
    public void testCountNatureHours() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2020-12-31 12:12:12");
        assertEquals(DateCountInterface.countNatureHours(date1, date2), 12);
        assertEquals(DateCountInterface.countNatureHours(date2, date1), -12);
    }

    /**
     * Test method for {@link DateCountInterface#countNatureMinutes(Date, Date)} (Date, Date)}
     */
    @Test
    public void testCountNatureMinutes() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00");
        Date date2 = DateCountInterface.parse("2020-12-31 00:12:12");
        assertEquals(DateCountInterface.countNatureMinutes(date1, date2), 12);
        assertEquals(DateCountInterface.countNatureMinutes(date2, date1), -12);
    }

    /**
     * Test method for {@link DateCountInterface#countNatureSeconds(Date, Date)} (Date, Date)}
     */
    @Test
    public void testCountNatureSeconds() {
        Date date1 = DateCountInterface.parse("2020-12-31 00:00:00.111");
        Date date2 = DateCountInterface.parse("2020-12-31 00:00:12.333");
        assertEquals(DateCountInterface.countNatureSeconds(date1, date2), 12);
        assertEquals(DateCountInterface.countNatureSeconds(date2, date1), -12);
    }

    /**
     * Test method for {@link DateCountInterface#getDatesBetween(Date, Date)}
     */
    @Test
    public void testGetDatesBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 11:00:00");
        Date date2 = DateCountInterface.parse("2021-01-02 22:00:00");
        List<Date> datesBetween = DateCountInterface.getDatesBetween(date1, date2);
        assertEquals(datesBetween.size(), 3);
        assertEquals(datesBetween.get(0), DateCountInterface.parse("2020-12-31 00:00:00"));
        assertEquals(datesBetween.get(1), DateCountInterface.parse("2021-01-01 00:00:00"));
        assertEquals(datesBetween.get(2), DateCountInterface.parse("2021-01-02 00:00:00"));
    }

    /**
     * Test method for {@link DateCountInterface#getHoursBetween(Date, Date)}
     */
    @Test
    public void testGetHoursBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 11:12:00");
        Date date2 = DateCountInterface.parse("2020-12-31 22:12:00");
        List<Date> hoursBetween = DateCountInterface.getHoursBetween(date1, date2);
        assertEquals(hoursBetween.size(), 12);
        assertEquals(hoursBetween.get(0), DateCountInterface.parse("2020-12-31 11:00:00"));
        assertEquals(hoursBetween.get(1), DateCountInterface.parse("2020-12-31 12:00:00"));
        assertEquals(hoursBetween.get(11), DateCountInterface.parse("2020-12-31 22:00:00"));
    }

    /**
     * Test method for {@link DateCountInterface#getMinutesBetween(Date, Date)}
     */
    @Test
    public void testGetMinutesBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 11:12:00");
        Date date2 = DateCountInterface.parse("2020-12-31 11:22:00");
        List<Date> minutesBetween = DateCountInterface.getMinutesBetween(date1, date2);
        assertEquals(minutesBetween.size(), 11);
        assertEquals(minutesBetween.get(0), DateCountInterface.parse("2020-12-31 11:12:00"));
        assertEquals(minutesBetween.get(1), DateCountInterface.parse("2020-12-31 11:13:00"));
        assertEquals(minutesBetween.get(9), DateCountInterface.parse("2020-12-31 11:21:00"));
    }

    /**
     * Test method for {@link DateCountInterface#getSecondsBetween(Date, Date)}
     */
    @Test
    public void testGetSecondsBetween() {
        Date date1 = DateCountInterface.parse("2020-12-31 11:12:00.111");
        Date date2 = DateCountInterface.parse("2020-12-31 11:12:10.333");
        List<Date> secondsBetween = DateCountInterface.getSecondsBetween(date1, date2);
        assertEquals(secondsBetween.size(), 11);
        assertEquals(secondsBetween.get(0), DateCountInterface.parse("2020-12-31 11:12:00"));
        assertEquals(secondsBetween.get(1), DateCountInterface.parse("2020-12-31 11:12:01"));
        assertEquals(secondsBetween.get(9), DateCountInterface.parse("2020-12-31 11:12:09"));
    }

}
