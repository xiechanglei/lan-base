package io.github.xiechanglei.lan.lang.date;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 时间之间的计算
 */
public class DateCountInterface extends DateBuilderInterface {

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
            dates.add(afterDays(start, i));
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
            dates.add(afterHours(start, i));
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
            dates.add(afterMinutes(start, i));
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
            dates.add(afterSeconds(start, i));
        }
        return dates;
    }

}
