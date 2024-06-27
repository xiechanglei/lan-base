package io.github.xiechanglei.lan.digest.crc;

/**
 * CRC32校验,默认多项式为0xEDB88320L
 * 0x04C11DB7
 */
public class CRC32 {

    private static final long DEFAULT_POLYNOMIAL = 0xEDB88320L;
    private final long polynomial;
    private final long[] crcTable = new long[256];


    public CRC32() {
        this(DEFAULT_POLYNOMIAL);
    }

    public CRC32(long polynomial) {
        this.polynomial = polynomial;
        this.initTable();
    }

    private void initTable() {
        for (int i = 0; i < 256; i++) {
            long crc = i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 1) == 1) {
                    crc = (crc >> 1) ^ this.polynomial;
                } else {
                    crc >>= 1;
                }
            }
            crcTable[i] = crc;
        }
    }

    private long crc = 0xFFFFFFFFL;

    public byte[] update(byte[] data) {
        if (data != null) {
            for (byte b : data) {
                update(b);
            }
        }
        return data;
    }

    public byte update(byte b) {
        calculate(b);
        return b;
    }

    public long getValue() {
        return crc ^ 0xFFFFFFFFL;
    }

    public byte[] getByteArrayValue() {
        long value = getValue();
        return new byte[]{(byte) ((value >> 24) & 0xFF), (byte) ((value >> 16) & 0xFF), (byte) ((value >> 8) & 0xFF), (byte) (value & 0xFF)};
    }

    private void calculate(byte b) {
        crc = (crc >> 8) ^ crcTable[(int) ((crc ^ b) & 0xFF)];
    }

    public static long getCrc32(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static long getCrc32(byte[] data, long polynomial) {
        CRC32 crc32 = new CRC32(polynomial);
        crc32.update(data);
        return crc32.getValue();
    }

    public static byte[] getCrc32ByteArray(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getByteArrayValue();
    }

    public static byte[] getCrc32ByteArray(byte[] data, long polynomial) {
        CRC32 crc32 = new CRC32(polynomial);
        crc32.update(data);
        return crc32.getByteArrayValue();
    }
}