package io.github.xiechanglei.lan.lang.date;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Calendar.*;

/**
 * 日期构建相关的接口
 */
public class DateBuilderInterface extends DateAfterInterface {

    public static DateBuilder getDateBuilder(Date date) {
        return new DateBuilder(date, DEFAULT_TIMEZONE);
    }

    public static DateBuilder getDateBuilder(Date date, TimeZone zone) {
        return new DateBuilder(date, zone);
    }

    public static Date startOfDay(Date date) {
        return getDateBuilder(date).startToDay().get();
    }

    public static Date endOfDay(Date date) {
        return getDateBuilder(date).endToDay().get();
    }

    public static Date startOfWeek(Date date) {
        return getDateBuilder(date).startToWeek().get();
    }

    public static Date endOfWeek(Date date) {
        return getDateBuilder(date).endToWeek().get();
    }

    public static Date startOfMonth(Date date) {
        return getDateBuilder(date).startToMonth().get();
    }

    public static Date endOfMonth(Date date) {
        return getDateBuilder(date).endToMonth().get();
    }

    public static Date startOfYear(Date date) {
        return getDateBuilder(date).startToYear().get();
    }

    public static Date endOfYear(Date date) {
        return getDateBuilder(date).endToYear().get();
    }

    public static Date startOfHour(Date date) {
        return getDateBuilder(date).startToHour().get();
    }

    public static Date endOfHour(Date date) {
        return getDateBuilder(date).endToHour().get();
    }

    public static Date startOfMinute(Date date) {
        return getDateBuilder(date).startToMinute().get();
    }

    public static Date endOfMinute(Date date) {
        return getDateBuilder(date).endToMinute().get();
    }

    public static Date startOfSecond(Date date) {
        return getDateBuilder(date).startToSecond().get();
    }

    public static Date endOfSecond(Date date) {
        return getDateBuilder(date).endToSecond().get();
    }

    public static class DateBuilder {
        private final Calendar calendar;

        private DateBuilder(Date date, TimeZone zone) {
            this.calendar = Calendar.getInstance(zone);
            this.calendar.setTime(date);
        }

        /**
         * 设置成当前毫秒数的开始
         */
        public DateBuilder startToSecond() {
            return this.set(MILLISECOND, 0);
        }

        /**
         * 设置成当前毫数的结束
         */
        public DateBuilder endToSecond() {
            return this.set(MILLISECOND, 999);
        }

        /**
         * 设置成当前分钟的开始
         */
        public DateBuilder startToMinute() {
            return this.set(SECOND, 0).startToSecond();
        }

        /**
         * 设置成当前分钟的结束
         */
        public DateBuilder endToMinute() {
            return this.set(SECOND, 59).endToSecond();
        }

        /**
         * 设置成当前小时的开始
         */
        public DateBuilder startToHour() {
            return this.set(MINUTE, 0).startToMinute();
        }

        /**
         * 设置成当前小时的结束
         */
        public DateBuilder endToHour() {
            return this.set(MINUTE, 59).endToMinute();
        }

        /**
         * 设置成当天开始的时间
         */
        public DateBuilder startToDay() {
            return this.set(HOUR_OF_DAY, 0).startToHour();
        }

        /**
         * 设置成当天结束的时间
         */
        public DateBuilder endToDay() {
            return this.set(HOUR_OF_DAY, 23).endToHour();
        }

        /**
         * 设置成当周第一天，周一
         */
        public DateBuilder startToWeek() {
            if (this.calendar.get(DAY_OF_WEEK) == SUNDAY) {
                this.add(DAY_OF_WEEK, -6);
            } else {
                this.set(DAY_OF_WEEK, 2);
            }
            return this.startToDay();
        }

        /**
         * 设置成当周最后一天，周日
         */
        public DateBuilder endToWeek() {
            if (this.calendar.get(DAY_OF_WEEK) != SUNDAY) {
                this.add(DAY_OF_WEEK, 8 - this.calendar.get(DAY_OF_WEEK));
            }
            return this.endToDay();
        }

        /**
         * 设置到当月第一天
         */
        public DateBuilder startToMonth() {
            return this.set(DAY_OF_MONTH, 1).startToDay();
        }

        /**
         * 设置到当月的最后一天
         */
        public DateBuilder endToMonth() {
            return this.add(MONTH, 1).set(DATE, 0).endToDay();
        }

        /**
         * 设置到当年第一天
         */
        public DateBuilder startToYear() {
            return this.set(DAY_OF_YEAR, 1).startToDay();
        }

        /**
         * 设置到当年的最后一天
         */
        public DateBuilder endToYear() {
            return this.set(MONTH, 11).endToMonth();
        }


        /**
         * 设置时间对应字段的值
         */
        public DateBuilder set(int field, int value) {
            this.calendar.set(field, value);
            return this;
        }

        /**
         * 增加时间对应字段的值
         */
        public DateBuilder add(int field, int value) {
            this.calendar.add(field, value);
            return this;
        }

        /**
         * 获取时间
         */
        public Date get() {
            return calendar.getTime();
        }
    }

}
