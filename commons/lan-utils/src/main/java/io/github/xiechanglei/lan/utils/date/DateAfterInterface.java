package io.github.xiechanglei.lan.utils.date;

import java.util.Date;

/**
 * 日期加上一定类型的相关参数之后的接口
 */
public class DateAfterInterface extends DateParseInterface{

    /**
     * 获取一定天数之后的日期，负数表示一定天数之前的日期
     */
    public static Date afterDays(Date date, int days) {
        return new Date(date.getTime() + (long) days * 24 * 60 * 60 * 1000);
    }

    /**
     * 以当前时间为日期标准，获取一定天数之后的日期，负数表示一定天数之前的日期
     */
    public static Date afterDays(int days) {
        return afterDays(new Date(), days);
    }

    /**
     * 获取一定小时数之后的日期，负数表示一定小时数之前的日期
     */
    public static Date afterHours(Date date, int hours) {
        return new Date(date.getTime() + (long) hours * 60 * 60 * 1000);
    }

    /**
     * 以当前时间为日期标准，获取一定小时数之后的日期，负数表示一定小时数之前的日期
     */
    public static Date afterHours(int hours) {
        return afterHours(new Date(), hours);
    }

    /**
     * 获取一定分钟数之后的日期，负数表示一定分钟数之前的日期
     */
    public static Date afterMinutes(Date date, int minutes) {
        return new Date(date.getTime() + (long) minutes * 60 * 1000);
    }

    /**
     * 以当前时间为日期标准，获取一定分钟数之后的日期，负数表示一定分钟数之前的日期
     */
    public static Date afterMinutes(int minutes) {
        return afterMinutes(new Date(), minutes);
    }

    /**
     * 获取一定秒数之后的日期，负数表示一定秒数之前的日期
     */
    public static Date afterSeconds(Date date, int seconds) {
        return new Date(date.getTime() + (long) seconds * 1000);
    }

    /**
     * 以当前时间为日期标准，获取一定秒数之后的日期，负数表示一定秒数之前的日期
     */
    public static Date afterSeconds(int seconds) {
        return afterSeconds(new Date(), seconds);
    }

    /**
     * 获取一定毫秒数之后的日期，负数表示一定毫秒数之前的日期
     */
    public static Date afterMilliseconds(Date date, int milliseconds) {
        return new Date(date.getTime() + (long) milliseconds);
    }

    /**
     * 以当前时间为日期标准，获取一定毫秒数之后的日期，负数表示一定毫秒数之前的日期
     */
    public static Date afterMilliseconds(int milliseconds) {
        return afterMilliseconds(new Date(), milliseconds);
    }
}
