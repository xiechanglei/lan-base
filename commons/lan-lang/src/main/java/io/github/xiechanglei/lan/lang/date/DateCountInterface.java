package io.github.xiechanglei.lan.lang.date;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间之间的计算
 */
public class DateCountInterface extends DateBuilderInterface {

    /**
     * 计算两个日期之间相隔年数,自然年数,比如2019-01-01和2020-02-22相隔一年
     */
    public static int countNatureYears(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
    }

    /**
     * 获取两个日期之间的所有年份,包括起始日期和结束日期
     */
    public static List<Date> getYearsBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureYears(start, end); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.YEAR, i);
            dates.add(startOfYear(c.getTime()));
        }
        return dates;
    }

    /**
     * 计算两个日期之间相隔月数,自然月数,比如2019-01-01和2019-02-22相隔一个月
     */
    public static int countNatureMonths(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
    }

    /**
     * 获取两个日期之间的所有月份,包括起始日期和结束日期
     */
    public static List<Date> getMonthsBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureMonths(start, end); i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.MONTH, i);
            dates.add(startOfMonth(c.getTime()));
        }
        return dates;
    }

    /**
     * 计算两个日期之间相隔的天数,自然天数,比如2019-01-01 23:59:59和2019-01-02 00:00:00相隔一天
     */
    public static int countNatureDays(Date d1, Date d2) {
        return (int) ((startOfDay(d2).getTime() - startOfDay(d1).getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 获取两个日期之间的所有日期(天),包括起始日期和结束日期
     */
    public static List<Date> getDatesBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureDays(start, end); i++) {
            dates.add(startOfDay(afterDays(start, i)));
        }
        return dates;
    }

    /**
     * 计算两个日期之间相隔的小时数,自然小时数,比如2019-01-01 23:59:59和2019-01-02 00:00:00相隔一小时
     */
    public static int countNatureHours(Date d1, Date d2) {
        return (int) ((startOfHour(d2).getTime() - startOfHour(d1).getTime()) / (60 * 60 * 1000));
    }

    /**
     * 获取两个日期之间的所有小时,包括起始日期和结束日期
     */
    public static List<Date> getHoursBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureHours(start, end); i++) {
            dates.add(startOfHour(afterHours(start, i)));
        }
        return dates;
    }


    /**
     * 计算两个日期之间相隔的分钟数,自然分钟数,比如2019-01-01 23:59:59和2019-01-02 00:00:00相隔一分钟
     */
    public static int countNatureMinutes(Date d1, Date d2) {
        return (int) ((startOfMinute(d2).getTime() - startOfMinute(d1).getTime()) / (60 * 1000));
    }


    /**
     * 获取两个日期之间所有的分钟
     */
    public static List<Date> getMinutesBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureMinutes(start, end); i++) {
            dates.add(startOfMinute(afterMinutes(start, i)));
        }
        return dates;
    }

    /**
     * 计算两个日期之间相隔的秒数,自然秒数,比如2019-01-01 23:59:59和2019-01-02 00:00:00相隔一秒
     */
    public static int countNatureSeconds(Date d1, Date d2) {
        return (int) ((startOfSecond(d2).getTime() - startOfSecond(d1).getTime()) / 1000);
    }

    /**
     * 获取两个日期之间所有的秒
     */
    public static List<Date> getSecondsBetween(Date d1, Date d2) {
        List<Date> dates = new ArrayList<>();
        Date start = d1, end = d2;
        if (d1.getTime() > d2.getTime()) {
            start = d2;
            end = d1;
        }
        for (int i = 0; i <= countNatureSeconds(start, end); i++) {
            dates.add(startOfSecond(afterSeconds(start, i)));
        }
        return dates;
    }

}
