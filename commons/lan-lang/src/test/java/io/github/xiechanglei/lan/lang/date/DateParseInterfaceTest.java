package io.github.xiechanglei.lan.lang.date;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateParseInterfaceTest {
    /**
     * Test method for {@link DateParseInterface#parse(String)}
     */
    @Test
    public void testParseDate() {
        Date parse = DateParseInterface.parse("2020-01-01");
        assertEquals(1577808000000L, parse.getTime());
    }

    /**
     * Test method for {@link DateParseInterface#parse(String)}
     */
    @Test
    public void testParseDateTime() {
        Date parse = DateParseInterface.parse("2020-01-01 00:00:01");
        assertEquals(1577808001000L, parse.getTime());
    }

    /**
     * Test method for {@link DateParseInterface#parse(String)}
     */
    @Test
    public void testParseDateTimeMs() {
        Date parse = DateParseInterface.parse("2020-01-01 00:00:01.001");
        assertEquals(1577808001001L, parse.getTime());
    }

    /**
     * Test method for {@link DateParseInterface#parse(String)}
     */
    @Test
    public void testParseWithNull() {
        assertThrows(IllegalArgumentException.class, () -> DateParseInterface.parse(null));
    }

    /**
     * Test method for {@link DateParseInterface#parse(String)}
     */
    @Test
    public void testParseWithInvalidFormat() {
        assertThrows(DateTimeParseException.class, () -> DateParseInterface.parse("2020-01-01 00:00:99"));
    }

    /**
     * Test method for {@link DateParseInterface#format(Date)}
     */
    @Test
    public void testFormat() {
        Date date = new Date(1577808001000L);
        assertEquals("2020-01-01 00:00:01", DateParseInterface.format(date));
    }

    /**
     * Test method for {@link DateParseInterface#buildConverter(String)}
     */
    @Test
    public void testBuildConverter() {
        DateParseInterface.DateConverter dateConverter = DateParseInterface.buildConverter("yyyy-MM-dd HH:mm:ss");
        Date parse = dateConverter.parse("2020-01-01 00:00:01");
        assertEquals(1577808001000L, parse.getTime());
        String format = dateConverter.format(parse);
        assertEquals("2020-01-01 00:00:01", format);
    }


}
