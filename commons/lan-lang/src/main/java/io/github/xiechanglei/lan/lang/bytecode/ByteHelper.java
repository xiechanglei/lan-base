package io.github.xiechanglei.lan.lang.bytecode;

/**
 * 字节工具类
 */
public class ByteHelper {
    /**
     * 将16进制字符串转换为byte
     */
    public static byte fromHex(String hexString) {
        if (hexString == null || hexString.length() != 2) {
            throw new IllegalArgumentException("hexString length must be 2");
        }
        return (byte) Integer.parseInt(hexString, 16);
    }

    /**
     * 将byte转换为16进制字符串
     */
    public static String toHex(byte b) {
        return String.format("%02X", b);
    }

}
