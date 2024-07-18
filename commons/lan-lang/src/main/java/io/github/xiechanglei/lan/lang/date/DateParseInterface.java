package io.github.xiechanglei.lan.lang.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间格式化与解析的接口
 */
public class DateParseInterface {
    public static final String DEFAULT_ZONE = "Asia/Shanghai";
    public static final TimeZone DEFAULT_TIMEZONE = TimeZone.getTimeZone(DEFAULT_ZONE);
    public static final ZoneId DEFAULT_ZONE_ID = ZoneId.of(DEFAULT_ZONE);
    // simple Date format
    public static final DateConverter DEFAULT_DATETIME_MS_PARSER = DateConverter.of("yyyy-MM-dd HH:mm:ss.SSS", DEFAULT_ZONE_ID);
    public static final DateConverter DEFAULT_DATETIME_PARSER = DateConverter.of("yyyy-MM-dd HH:mm:ss", DEFAULT_ZONE_ID);
    public static final DateConverter DEFAULT_DATE_PARSER = DateConverter.of("yyyy-MM-dd", DEFAULT_ZONE_ID);
    // default ZONE

    /**
     * 按照默认的格式解析日期，支持的格式有 yyyy-MM-dd、yyyy-MM-dd HH:mm:ss、yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param dateStr 日期字符串
     * @return 解析后的日期
     * @throws IllegalArgumentException dateStr为null或者不是默认的几个格式长度的时候，抛出异常
     * @throws DateTimeParseException   解析字符串失败
     */
    public static Date parse(String dateStr) {
        if (dateStr == null) {
            throw new IllegalArgumentException("dateStr must not be null");
        }
        if (dateStr.length() == 10) {
            return DEFAULT_DATE_PARSER.parseDate(dateStr);
        } else if (dateStr.length() == 19) {
            return DEFAULT_DATETIME_PARSER.parseDateTime(dateStr);
        } else if (dateStr.length() == 23) {
            return DEFAULT_DATETIME_MS_PARSER.parseDateTime(dateStr);
        } else {
            throw new IllegalArgumentException(" if you want to parse string with default format,the string format must like yyyy-MM-dd or yyyy-MM-dd HH:mm:ss or yyyy-MM-dd HH:mm:ss.SSS");
        }
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式来格式化日期，因为常规情况下，这是日期格式化的最常用的格式
     *
     * @param date 日期
     * @return 格式化后的日期字符串
     */
    public static String format(Date date) {
        return DEFAULT_DATETIME_PARSER.format(date);
    }

    /**
     * 以默认时区构建解析日期的解析器，在比较高使用频率的情况下，可以使用该方法构建解析器之后存为全局变量，避免重复构建解析器
     * @param pattern 时间格式
     * @return DateConverter解析器，用于解析时间或者格式化时间
     */
    public static DateConverter buildConverter(String pattern) {
        return DateConverter.of(pattern, DEFAULT_ZONE_ID);
    }

    /**
     * 以指定时区构建解析日期的解析器，在比较高使用频率的情况下，可以使用该方法构建解析器之后存为全局变量，避免重复构建解析器
     * @param pattern 时间格式
     * @param zoneId 时区
     * @return DateConverter解析器，用于解析时间或者格式化时间
     */
    public static DateConverter buildConverter(String pattern, ZoneId zoneId) {
        return DateConverter.of(pattern, zoneId);
    }

    /**
     * 时间解析器
     */
    public static class DateConverter {
        // 时间格式化器
        private DateTimeFormatter formatter;
        // 时区
        private ZoneId zoneId;
        // 是否只有日期
        private boolean onlyDate;

        private DateConverter() {
        }

        protected static DateConverter of(String pattern, ZoneId zoneId) {
            DateConverter dateConverter = new DateConverter();
            dateConverter.onlyDate = !(pattern.contains("HH") || pattern.contains("mm") || pattern.contains("ss") || pattern.contains("SSS"));
            dateConverter.formatter = DateTimeFormatter.ofPattern(pattern);
            dateConverter.zoneId = zoneId;
            return dateConverter;
        }

        /**
         * 格式化时间
         */
        public String format(Date date) {
            return this.formatter.format(LocalDateTime.ofInstant(date.toInstant(), this.zoneId));
        }

        /**
         * 根据pattern自动判断是解析日期还是解析时间，最好少用，知道解析什么类型就用什么类型
         */
        public Date parse(String dateStr) {
            return onlyDate ? this.parseDate(dateStr) : this.parseDateTime(dateStr);
        }

        /**
         * 解析日期,pattern 中只有年月日参数
         */
        private Date parseDate(String dateStr) {
            LocalDate localDate = LocalDate.parse(dateStr, this.formatter);
            return Date.from(localDate.atStartOfDay().atZone(this.zoneId).toInstant());
        }

        /**
         * 解析时间，pattern 中有年月日，时分秒字段，
         */
        private Date parseDateTime(String dateStr) {
            LocalDateTime localDate = LocalDateTime.parse(dateStr, this.formatter);
            return Date.from(localDate.atZone(this.zoneId).toInstant());
        }

    }

}
