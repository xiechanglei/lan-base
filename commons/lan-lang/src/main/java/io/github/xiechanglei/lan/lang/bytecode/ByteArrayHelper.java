package io.github.xiechanglei.lan.lang.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * 字节数组工具类
 */
public class ByteArrayHelper {
    /**
     * 将int数组转换为byte数组
     * for example:
     * byte[] bytes = ByteArrayHelper.fromNumbers(12, 13);
     *
     * @param bytes int数组
     * @return byte数组
     */
    public static byte[] fromNumbers(int... bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) bytes[i];
        }
        return result;
    }

    /**
     * 将16进制字符串转换为byte数组,
     * for example:
     * byte[] bytes = ByteArrayHelper.fromHex("0A0B0C");
     *
     * @param hexString 16进制字符串
     * @return byte数组
     */
    public static byte[] fromHex(String hexString) {
        if (hexString == null) {
            return new byte[0];
        }
        hexString = hexString.replaceAll("[\\s\r]+", "");
        if (hexString.isEmpty()) {
            return new byte[0];
        }
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("hexString length must be even");
        }
        byte[] result = new byte[hexString.length() / 2];
        char[] charArray = hexString.toCharArray();
        for (int i = 0; i < hexString.length(); i += 2) {
            result[i / 2] = (byte) Integer.parseInt(new String(new char[]{charArray[i], charArray[i + 1]}), 16);
        }
        return result;
    }

    /**
     * 将字节数组转换为16进制字符串
     * for example:
     * String hex = ByteArrayHelper.toHex(new byte[]{10, 11, 12});
     *
     * @param bytes 字节数组
     * @return 16进制字符串
     */
    public static String toHex(byte[] bytes) {
        return toHex(bytes, 0, bytes.length);
    }

    /**
     * 将字节数组转换为16进制字符串
     *
     * @param bytes 字节数组
     * @param end   结束位置
     * @return 16进制字符串
     */
    public static String toHex(byte[] bytes, int end) {
        return toHex(bytes, 0, end);
    }

    /**
     * 将字节数组转换为16进制字符串
     *
     * @param bytes 字节数组
     * @param start 开始位置
     * @param end   结束位置
     * @return 16进制字符串
     */
    public static String toHex(byte[] bytes, int start, int end) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        if (start < 0 || start >= bytes.length) {
            throw new IllegalArgumentException("start must be in [0, bytes.length)");
        }
        if (end < 0 || end > bytes.length) {
            throw new IllegalArgumentException("end must be in [0, bytes.length]");
        }
        if (start >= end) {
            throw new IllegalArgumentException("start must be less than end");
        }
        StringBuilder sb = new StringBuilder();
        end = Math.min(end, bytes.length);
        for (int i = start; i < end; i++) {
            sb.append(ByteHelper.toHex(bytes[i]));
        }
        return sb.toString();
    }

    /**
     * 判断两个字节数组是否相等
     */
    public static boolean isSame(byte[] bytes1, byte[] bytes2) {
        if (bytes1 == null && bytes2 == null) {
            return true;
        }
        if (bytes1 == null || bytes2 == null) {
            return false;
        }
        if (bytes1.length != bytes2.length) {
            return false;
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 从字节数组中查找指定字节
     *
     * @param byteArr 源字节数组
     * @param target  目标字节
     * @return 位置
     */
    public static int indexOf(byte[] byteArr, byte target) {
        for (int i = 0; i < byteArr.length; i++) {
            if (byteArr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 从字节数组中查找指定字节
     *
     * @param byteArr 源字节数组
     * @param target  目标字节
     * @return 位置
     */
    public static int lastIndexOf(byte[] byteArr, byte target) {
        for (int i = byteArr.length - 1; i >= 0; i--) {
            if (byteArr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * short 转换
     */
    public static short toShort(byte[] bytes) {
        if (bytes == null || bytes.length != 2) {
            throw new IllegalArgumentException("convert short from bytes length must be 2");
        }
        short result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += (short) ((bytes[i] & 0xFF) << (8 * (bytes.length - i - 1)));
        }
        return result;
    }

    public static int toUnsignedShort(byte[] bytes) {
        return toShort(bytes) & 0xFFFF;
    }

    public static byte[] fromShort(short value) {
        return new byte[]{(byte) ((value >> 8) & 0xFF), (byte) (value & 0xFF)};
    }

    public static byte[] fromUnsignedShort(int value) {
        return fromShort((short) value);
    }

    /**
     * int 转换
     */
    public static int toInt(byte[] bytes) {
        if (bytes == null || bytes.length != 4) {
            throw new IllegalArgumentException("convert int from bytes length must be 4");
        }
        int result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += (bytes[i] & 0xFF) << (8 * (bytes.length - i - 1));
        }
        return result;
    }

    public static long toUnsignedInt(byte[] bytes) {
        return toInt(bytes) & 0xFFFFFFFFL;
    }

    public static byte[] fromInt(int value) {
        return new byte[]{(byte) ((value >> 24) & 0xFF), (byte) ((value >> 16) & 0xFF), (byte) ((value >> 8) & 0xFF), (byte) (value & 0xFF)};
    }

    public static byte[] fromUnsignedInt(long value) {
        return fromInt((int) value);
    }


    /**
     * long 转换
     */
    public static long toLong(byte[] bytes) {
        if (bytes == null || bytes.length != 8) {
            throw new IllegalArgumentException("convert long from bytes length must be 8");
        }
        long result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += (long) (bytes[i] & 0xFF) << (8 * (bytes.length - i - 1));
        }
        return result;
    }

    public static byte[] fromLong(long value) {
        return new byte[]{(byte) ((value >> 56) & 0xFF), (byte) ((value >> 48) & 0xFF), (byte) ((value >> 40) & 0xFF), (byte) ((value >> 32) & 0xFF), (byte) ((value >> 24) & 0xFF), (byte) ((value >> 16) & 0xFF), (byte) ((value >> 8) & 0xFF), (byte) (value & 0xFF)};
    }

    /**
     * float 转换
     */
    public static float toFloat(byte[] bytes) {
        if (bytes == null || bytes.length != 4) {
            throw new IllegalArgumentException("convert float from bytes length must be 4");
        }
        int result = toInt(bytes);
        return Float.intBitsToFloat(result);
    }

    public static byte[] fromFloat(float value) {
        int result = Float.floatToIntBits(value);
        return fromInt(result);
    }

    /**
     * double 转换
     */
    public static double toDouble(byte[] bytes) {
        if (bytes == null || bytes.length != 8) {
            throw new IllegalArgumentException("convert double from bytes length must be 8");
        }
        long result = toLong(bytes);
        return Double.longBitsToDouble(result);
    }

    public static byte[] fromDouble(double value) {
        long result = Double.doubleToLongBits(value);
        return fromLong(result);
    }

    /**
     * 合并两个字节数组
     */
    public static byte[] merge(byte[]... bytes) {
        int length = 0;
        for (byte[] aByte : bytes) {
            length += aByte.length;
        }
        byte[] result = new byte[length];
        int index = 0;
        for (byte[] aByte : bytes) {
            System.arraycopy(aByte, 0, result, index, aByte.length);
            index += aByte.length;
        }
        return result;
    }

    /**
     * 转list
     */
    public static List<Byte> toList(byte[] bytes, int start, int end) {
        List<Byte> list = new ArrayList<>();
        for (int i = start; i < end; i++) {
            list.add(bytes[i]);
        }
        return list;
    }

    public static List<Byte> toList(byte[] bytes) {
        return toList(bytes, 0, bytes.length);
    }


    /**
     * gzip
     */
    public static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(data);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
