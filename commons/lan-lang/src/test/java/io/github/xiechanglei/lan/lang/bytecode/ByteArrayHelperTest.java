package io.github.xiechanglei.lan.lang.bytecode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ByteArrayHelperTest {

    /**
     * Test method for {@link ByteArrayHelper#fromNumbers(int...)}.
     */
    @Test
    public void testFromNumbers() {
        byte[] bytes = ByteArrayHelper.fromNumbers(12, 13);
        assertEquals(2, bytes.length);
        assertEquals(12, bytes[0]);
        assertEquals(13, bytes[1]);
    }

    /**
     * Test method for {@link ByteArrayHelper#fromHex(String)}.
     */
    @Test
    public void testFromHexString() {
        byte[] bytes = ByteArrayHelper.fromHex("0A0B");
        assertEquals(2, bytes.length);
        assertEquals(10, bytes[0]);
        assertEquals(11, bytes[1]);
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[])}.
     */
    @Test
    public void testToHexString() {
        byte[] bytes = new byte[]{10, 11};
        assertEquals("0A0B", ByteArrayHelper.toHex(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[], int)}.
     */
    @Test
    public void testToHexStringWithEnd() {
        byte[] bytes = new byte[]{10, 11};
        assertEquals("0A", ByteArrayHelper.toHex(bytes, 1));
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[], int, int)}.
     */
    @Test
    public void testToHexStringWithStartAndEnd() {
        byte[] bytes = new byte[]{10, 11};
        assertEquals("0A", ByteArrayHelper.toHex(bytes, 0, 1));
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[], int, int)}.
     */
    @Test
    public void testToHexStringWithStartAndEnd2() {
        byte[] bytes = new byte[]{10, 11};
        assertEquals("0A0B", ByteArrayHelper.toHex(bytes, 0, 2));
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[], int, int)}.
     */
    @Test
    public void testToHexStringWithStartAndEnd3() {
        byte[] bytes = new byte[]{10, 11};
        assertEquals("0B", ByteArrayHelper.toHex(bytes, 1, 2));
    }

    /**
     * Test method for {@link ByteArrayHelper#toHex(byte[], int, int)}.
     */
    @Test
    public void testToHexStringWithStartAndEnd4() {
        byte[] bytes = new byte[]{10, 11};
        assertThrows(IllegalArgumentException.class, () -> ByteArrayHelper.toHex(bytes, 0, 3));
    }

    /**
     * Test method for {@link ByteArrayHelper#isSame(byte[], byte[])}.
     */
    @Test
    public void testIsSame() {
        byte[] bytes1 = new byte[]{10, 11};
        byte[] bytes2 = new byte[]{10, 11};
        assertTrue(ByteArrayHelper.isSame(bytes1, bytes2));
    }

    /**
     * Test method for {@link ByteArrayHelper#isSame(byte[], byte[])}.
     */
    @Test
    public void testIsSame2() {
        byte[] bytes1 = new byte[]{10, 11};
        byte[] bytes2 = new byte[]{10, 12};
        assertFalse(ByteArrayHelper.isSame(bytes1, bytes2));
    }

    /**
     * Test method for {@link ByteArrayHelper#isSame(byte[], byte[])}.
     */
    @Test
    public void testIsSame3() {
        byte[] bytes1 = new byte[]{10, 11};
        byte[] bytes2 = new byte[]{10};
        assertFalse(ByteArrayHelper.isSame(bytes1, bytes2));
        assertFalse(ByteArrayHelper.isSame(bytes2, bytes1));
    }

    /**
     * Test method for {@link ByteArrayHelper#isSame(byte[], byte[])}.
     */
    @Test
    @SuppressWarnings("ConstantConditions")
    public void testIsSame4() {
        byte[] bytes1 = new byte[]{10};
        assertFalse(ByteArrayHelper.isSame(bytes1, null));
        assertFalse(ByteArrayHelper.isSame(null, bytes1));
        assertTrue(ByteArrayHelper.isSame(null, null));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromShort(short)} and {@link ByteArrayHelper#toShort(byte[])}.
     */
    @Test
    public void testConvertWithShort() {
        short s = 1;
        byte[] bytes = ByteArrayHelper.fromShort(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{0, 1}, bytes));
        assertEquals(s, ByteArrayHelper.toShort(bytes));

        s = -1;
        bytes = ByteArrayHelper.fromShort(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-1, -1}, bytes));
        assertEquals(s, ByteArrayHelper.toShort(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromUnsignedShort(int)} and {@link ByteArrayHelper#toUnsignedShort(byte[])}.
     */
    @Test
    public void testConvertWithUnsignedShort() {
        int s = 1;
        byte[] bytes = ByteArrayHelper.fromUnsignedShort(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{0, 1}, bytes));
        assertEquals(s, ByteArrayHelper.toUnsignedShort(bytes));

        s = 65535;
        bytes = ByteArrayHelper.fromUnsignedShort(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-1, -1}, bytes));
        assertEquals(s, ByteArrayHelper.toUnsignedShort(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromInt(int)} and {@link ByteArrayHelper#toInt(byte[])}.
     */
    @Test
    public void testConvertWithInt() {
        int s = 1;
        byte[] bytes = ByteArrayHelper.fromInt(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{0, 0, 0, 1}, bytes));
        assertEquals(s, ByteArrayHelper.toInt(bytes));

        s = -1;
        bytes = ByteArrayHelper.fromInt(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-1, -1, -1, -1}, bytes));
        assertEquals(s, ByteArrayHelper.toInt(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromUnsignedInt(long)} and {@link ByteArrayHelper#toUnsignedInt(byte[])}.
     */
    @Test
    public void testConvertWithUnsignedInt() {
        long s = 1;
        byte[] bytes = ByteArrayHelper.fromUnsignedInt(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{0, 0, 0, 1}, bytes));
        assertEquals(s, ByteArrayHelper.toUnsignedInt(bytes));

        s = 4294967295L;
        bytes = ByteArrayHelper.fromUnsignedInt(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-1, -1, -1, -1}, bytes));
        assertEquals(s, ByteArrayHelper.toUnsignedInt(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromLong(long)} and {@link ByteArrayHelper#toLong(byte[])}.
     */
    @Test
    public void testConvertWithLong() {
        long s = 1;
        byte[] bytes = ByteArrayHelper.fromLong(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{0, 0, 0, 0, 0, 0, 0, 1}, bytes));
        assertEquals(s, ByteArrayHelper.toLong(bytes));

        s = -1;
        bytes = ByteArrayHelper.fromLong(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-1, -1, -1, -1, -1, -1, -1, -1}, bytes));
        assertEquals(s, ByteArrayHelper.toLong(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromFloat(float)} and {@link ByteArrayHelper#toFloat(byte[])}.
     */
    @Test
    public void testConvertWithFloat() {
        float s = 1.0f;
        byte[] bytes = ByteArrayHelper.fromFloat(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{63, -128, 0, 0}, bytes));
        assertEquals(s, ByteArrayHelper.toFloat(bytes));

        s = -1.0f;
        bytes = ByteArrayHelper.fromFloat(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-65, -128, 0, 0}, bytes));
        assertEquals(s, ByteArrayHelper.toFloat(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#fromDouble(double)} and {@link ByteArrayHelper#toDouble(byte[])}.
     */
    @Test
    public void testConvertWithDouble() {
        double s = 1.0;
        byte[] bytes = ByteArrayHelper.fromDouble(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{63, -16, 0, 0, 0, 0, 0, 0}, bytes));
        assertEquals(s, ByteArrayHelper.toDouble(bytes));

        s = -1.0;
        bytes = ByteArrayHelper.fromDouble(s);
        assertTrue(ByteArrayHelper.isSame(new byte[]{-65, -16, 0, 0, 0, 0, 0, 0}, bytes));
        assertEquals(s, ByteArrayHelper.toDouble(bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#merge(byte[]...)} .
     */
    @Test
    public void testMerge() {
        byte[] bytes1 = new byte[]{1, 2};
        byte[] bytes2 = new byte[]{3, 4};
        byte[] bytes3 = new byte[]{5, 6};
        byte[] bytes = ByteArrayHelper.merge(bytes1, bytes2, bytes3);
        assertTrue(ByteArrayHelper.isSame(new byte[]{1, 2, 3, 4, 5, 6}, bytes));
    }

    /**
     * Test method for {@link ByteArrayHelper#toList(byte[])}
     */
    @Test
    public void testToList() {
        byte[] bytes = new byte[]{1, 2, 3, 4};
        assertEquals(4, ByteArrayHelper.toList(bytes).size());
    }

    /**
     * Test method for {@link ByteArrayHelper#indexOf(byte[], byte)}
     */
    @Test
    public void testIndexOf() {
        byte[] bytes = new byte[]{1, 2, 3, 4};
        assertEquals(1, ByteArrayHelper.indexOf(bytes, (byte) 2));
        assertEquals(-1, ByteArrayHelper.indexOf(bytes, (byte) 5));
    }

    /**
     * Test method for {@link ByteArrayHelper#lastIndexOf(byte[], byte)}
     */
    @Test
    public void testLastIndexOf() {
        byte[] bytes = new byte[]{1, 2, 3, 4, 2};
        assertEquals(4, ByteArrayHelper.lastIndexOf(bytes, (byte) 2));
        assertEquals(-1, ByteArrayHelper.lastIndexOf(bytes, (byte) 5));
    }

}
