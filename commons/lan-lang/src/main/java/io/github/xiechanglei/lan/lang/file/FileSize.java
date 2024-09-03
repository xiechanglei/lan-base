package io.github.xiechanglei.lan.lang.file;

/**
 * 文件大小格式化
 *
 * @author xie
 * @date 2024/9/2
 */
public class FileSize {
    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};

    public static String format(long size) {
        int unit = 0;
        double s = size;
        while (s >= 1024 && unit < UNITS.length - 1) {
            s /= 1024;
            unit++;
        }
        return String.format("%.2f %s", s, UNITS[unit]);
    }
}
