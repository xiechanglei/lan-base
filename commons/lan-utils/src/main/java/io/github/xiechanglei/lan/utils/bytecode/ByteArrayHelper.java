package io.github.xiechanglei.lan.utils.bytecode;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节数组工具类
 */
public class ByteArrayHelper {
    /**
     * 将int数组转换为byte数组
     * for example:
     * byte[] bytes = ByteArrayHelper.fromNumbers(12, 13);
     */
    public static byte[] fromNumbers(int... bytes) {
        byte[] result = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = (byte) bytes[i];
        }
        return result;
    }

    /**
     * 将16进制字符串转换为byte数组
     */
    public static byte[] fromHexString(String hexString) {
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
     */
    public static String toHexString(byte[] bytes) {
        return toHexString(bytes, 0, bytes.length);
    }

    public static String toHexString(byte[] bytes, int end) {
        return toHexString(bytes, 0, end);
    }

    public static String toHexString(byte[] bytes, int start, int end) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        end = Math.min(end, bytes.length);
        for (int i = start; i < end; i++) {
            sb.append(ByteHelper.toHexString(bytes[i]));
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
     * short 转换
     */
    public static short toShort(byte[] bytes) {
        if (bytes == null || bytes.length != 2) {
            throw new RuntimeException("convert short from bytes length must be 2");
        }
        short result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result += (short) ((short) (bytes[i] & 0xFF) << (8 * (bytes.length - i - 1)));
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
            throw new RuntimeException("convert int from bytes length must be 4");
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
            throw new RuntimeException("convert long from bytes length must be 8");
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
            throw new RuntimeException("convert float from bytes length must be 4");
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
            throw new RuntimeException("convert double from bytes length must be 8");
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

}
