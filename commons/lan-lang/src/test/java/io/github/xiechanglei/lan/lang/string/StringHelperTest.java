package io.github.xiechanglei.lan.lang.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * test case for {@link StringHelper}
 */
public class StringHelperTest {

    /**
     * est case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertInteger() {
        assert 123456 ==  StringHelper.convert("123456",Integer.class);
        assert 12345678 == StringHelper.convert("12345678", Integer.class);
        assert 123456 == StringHelper.convert("123456",int.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12345678a", Integer.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertLong() {
        assert 123456L ==  StringHelper.convert("123456",Long.class);
        assert 12345678L == StringHelper.convert("12345678", Long.class);
        assert 123456L == StringHelper.convert("123456",long.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12345678a", Long.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertDouble() {
        assert 123456.0 == StringHelper.convert("123456", Double.class);
        assert 12345678.0 == StringHelper.convert("12345678", Double.class);
        assert 123456.0 == StringHelper.convert("123456", double.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12345678a", Double.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertFloat() {
        assert 123456.0f == StringHelper.convert("123456", Float.class);
        assert 12345678.0f == StringHelper.convert("12345678", Float.class);
        assert 123456.0f == StringHelper.convert("123456", float.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12345678a", Float.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertShort() {
        assert (short)12345 == StringHelper.convert("12345", Short.class);
        assert (short)12345 == StringHelper.convert("12345", short.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12345a", Short.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertByte() {
        assert (byte)123 == StringHelper.convert("123", Byte.class);
        assert (byte)12 == StringHelper.convert("12", Byte.class);
        assert (byte)12 == StringHelper.convert("12", byte.class);
        assertThrows(NumberFormatException.class, () -> StringHelper.convert("12a", Byte.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertBoolean() {
        assert true == StringHelper.convert("true", Boolean.class);
        assert false == StringHelper.convert("false", Boolean.class);
        assert true == StringHelper.convert("true", boolean.class);
        assert false == StringHelper.convert("false", boolean.class);
        assertThrows(IllegalArgumentException.class, () -> StringHelper.convert("true1", Boolean.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    @Test
    @SuppressWarnings("all")
    public void testConvertCharacter() {
        assert 'a' == StringHelper.convert("a", Character.class);
        assert 'b' == StringHelper.convert("b", Character.class);
        assert 'a' == StringHelper.convert("a", char.class);
        assertThrows(IllegalArgumentException.class, () -> StringHelper.convert("ab", Character.class));
    }

    /**
     * test case for {@link StringHelper#convert(String, Class)}
     */
    public enum TestEnum {A, B, C, D}
    @Test
    public void testConvertEnum() {
        assert TestEnum.A == StringHelper.convert("A", TestEnum.class);
        assert TestEnum.B == StringHelper.convert("B", TestEnum.class);
        assert TestEnum.C == StringHelper.convert("C", TestEnum.class);
        assert TestEnum.D == StringHelper.convert("D", TestEnum.class);
    }

    /**
     * test case for {@link StringHelper#isNotBlank(String)}
     */
    @Test
    @SuppressWarnings("all")
    public void testIsNotSame() {
        assert StringHelper.isNotSame("a", "b");
        assert !StringHelper.isNotSame("a", "a");
        assert StringHelper.isNotSame("a", "A");
        assert StringHelper.isNotSame("a", "B");
        assert StringHelper.isNotSame("a", null);
        assert StringHelper.isNotSame(null, "a");
    }

}
