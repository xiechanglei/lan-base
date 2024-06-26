package io.github.xiechanglei.lan.base.utils.string;

public class StringHelper {
    public static <T> Object convert(String value, Class<T> type) {
        if (type == String.class) {
            return value;
        }

        if (!isNotBlank(value)) {
            return null;
        }

        if (type == Integer.class) {
            return Integer.valueOf(value);
        }
        if (type == int.class) {
            return Integer.valueOf(value);
        }
        if (type == Long.class) {
            return Long.valueOf(value);
        }
        if (type == long.class) {
            return Long.valueOf(value);
        }
        if (type == Double.class) {
            return Double.valueOf(value);
        }
        if (type == double.class) {
            return Double.valueOf(value);
        }
        if (type == Float.class) {
            return Float.valueOf(value);
        }
        if (type == float.class) {
            return Float.valueOf(value);
        }
        if (type == Boolean.class) {
            return Boolean.valueOf(value);
        }
        if (type == boolean.class) {
            return Boolean.valueOf(value);
        }
        if (type == Byte.class) {
            return Byte.valueOf(value);
        }
        if (type == byte.class) {
            return Byte.valueOf(value);
        }
        if (type == Short.class) {
            return Short.valueOf(value);
        }
        if (type == short.class) {
            return Short.valueOf(value);
        }
        //如果是枚举类型
        if (type.isEnum()) {
            return Enum.valueOf((Class<Enum>) type, value);
        }
        return null;
    }

    /**
     * 比较两个字符串是否相等
     */
    public static boolean isNotSame(String a, String b) {
        if (a == null && b == null) {
            return false;
        }
        if (a != null) {
            return !a.equals(b);
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
