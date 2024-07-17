package io.github.xiechanglei.lan.lang.bytecode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ByteHelperTest {

    /**
     * 测试将16进制字符串转换为byte
     */
    @Test
    public void testFromHex() {
        assertEquals(ByteHelper.fromHex("FF"), -1);
        assertEquals(ByteHelper.fromHex("00"), 0);
        assertEquals(ByteHelper.fromHex("01"), 1);
        assertEquals(ByteHelper.fromHex("10"), 16);
        assertEquals(ByteHelper.fromHex("7F"), 127);
        assertEquals(ByteHelper.fromHex("80"), -128);
    }

    /**
     * 测试将byte转换为16进制字符串
     */
    @Test
    public void testToHex() {
        assertEquals(ByteHelper.toHex((byte) -1), "FF");
        assertEquals(ByteHelper.toHex((byte) 0), "00");
        assertEquals(ByteHelper.toHex((byte) 1), "01");
        assertEquals(ByteHelper.toHex((byte) 16), "10");
        assertEquals(ByteHelper.toHex((byte) 127), "7F");
        assertEquals(ByteHelper.toHex((byte) -128), "80");
    }
}
