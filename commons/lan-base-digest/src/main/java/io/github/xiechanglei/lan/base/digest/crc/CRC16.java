package io.github.xiechanglei.lan.base.digest.crc;

/**
 * CRC16校验
 */
public class CRC16 {


    int crc = 0xFFFF;

    public byte[] update(byte[] data) {
        if (data != null) {
            for (byte b : data) {
                update(b);
            }
        }
        return data;
    }

    public byte update(byte b) {
        this.crc = _ca(this.crc, b);
        return b;
    }

    public int getValue() {
        return this.crc;
    }

    public byte[] getByteArrayValue() {
        return getByteArrayValue(this.crc);
    }


    /**
     * 静态方法，直接计算CRC16
     */
    public static int calculate(byte[] data) {
        int crc = 0xFFFF;
        for (byte b : data) {
            crc = _ca(crc, b);
        }
        return crc;
    }

    public static byte[] calculateToByteArray(byte[] data) {
        return getByteArrayValue(calculate(data));
    }

    private static byte[] getByteArrayValue(int crc) {
        return new byte[]{(byte) (crc & 0xFF), (byte) ((crc >> 8) & 0xFF)};
    }

    private static int _ca(int c, byte b) {
        c ^= b & 0xFF;
        for (int i = 0; i < 8; i++) {
            if ((c & 1) == 1) {
                c = (c >>> 1) ^ 0xA001;
            } else {
                c = c >>> 1;
            }
        }
        return c;
    }
}