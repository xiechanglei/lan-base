package io.github.xiechanglei.lan.lang.collections;

import org.junit.jupiter.api.Test;

public class ArrayHelperTest {
    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcat() {
        Integer[] a = {1, 2, 3};
        Integer[] b = {4, 5, 6};
        Integer[] c = {7, 8, 9};
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 9;
        assert d[0] == 1;
        assert d[1] == 2;
        assert d[2] == 3;
        assert d[3] == 4;
        assert d[4] == 5;
        assert d[5] == 6;
        assert d[6] == 7;
        assert d[7] == 8;
        assert d[8] == 9;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithEmpty() {
        Integer[] a = {1, 2, 3};
        Integer[] b = {};
        Integer[] c = {7, 8, 9};
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 6;
        assert d[0] == 1;
        assert d[1] == 2;
        assert d[2] == 3;
        assert d[3] == 7;
        assert d[4] == 8;
        assert d[5] == 9;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithNull() {
        Integer[] a = {1, 2, 3};
        Integer[] b = null;
        Integer[] c = {7, 8, 9};
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 6;
        assert d[0] == 1;
        assert d[1] == 2;
        assert d[2] == 3;
        assert d[3] == 7;
        assert d[4] == 8;
        assert d[5] == 9;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithAllNull() {
        Integer[] a = null;
        Integer[] b = null;
        Integer[] c = null;
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d == null;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithAllEmpty() {
        Integer[] a = {};
        Integer[] b = {};
        Integer[] c = {};
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 0;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithAllEmptyAndNull() {
        Integer[] a = {};
        Integer[] b = null;
        Integer[] c = {};
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 0;
    }

    /**
     * test case for {@link ArrayHelper#concat(Object[]...)}
     */
    @Test
    public void testConcatWithAllNullAndEmpty() {
        Integer[] a = null;
        Integer[] b = {};
        Integer[] c = null;
        Integer[] d = ArrayHelper.concat(a, b, c);
        assert d.length == 0;
    }

    /**
     * test case for {@link ArrayHelper#distinct(Object[])}
     */
    @Test
    public void testDistinct() {
        Integer[] a = {1, 2, 3, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Integer[] b = ArrayHelper.distinct(a);
        assert b.length == 9;
        assert b[0] == 1;
        assert b[1] == 2;
        assert b[2] == 3;
        assert b[3] == 4;
        assert b[4] == 5;
        assert b[5] == 6;
        assert b[6] == 7;
        assert b[7] == 8;
        assert b[8] == 9;
    }
}
