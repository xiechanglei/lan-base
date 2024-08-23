package io.github.xiechanglei.lan.lang.string;

/**
 * 字符串工具类
 */
public class StringHelper {
    /**
     * 将字符串转换成指定类型
     * @param value 字符串
     * @param type 类型
     * @return 转换后的对象
     * @param <T> 类型
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(String value, Class<T> type) {
        if (type == String.class) {
            return (T) value;
        }

        if (!isNotBlank(value)) {
            return null;
        }

        if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(value);
        }
        if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(value);
        }
        if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(value);
        }
        if (type == Float.class|| type == float.class) {
            return (T) Float.valueOf(value);
        }
        if (type == Boolean.class || type == boolean.class) {
            if ("true".equalsIgnoreCase(value) ) {
                return (T) Boolean.TRUE;
            }else if ("false".equalsIgnoreCase(value)){
                return (T) Boolean.FALSE;
            }else {
                throw new IllegalArgumentException("无法转换为boolean类型");
            }
        }
        if (type == Byte.class || type == byte.class) {
            return (T) Byte.valueOf(value);
        }
        if (type == Short.class || type == short.class) {
            return (T) Short.valueOf(value);
        }
        if (type == Character.class|| type == char.class) {
            if (value.length() != 1) {
                throw new IllegalArgumentException("字符串长度不为1");
            }
            return (T) Character.valueOf(value.charAt(0));
        }
        //如果是枚举类型
        if (type.isEnum()) {
            return (T) Enum.valueOf((Class<Enum>) type, value);
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
