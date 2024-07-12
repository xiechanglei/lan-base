package io.github.xiechanglei.lan.lang.reflect;

import io.github.xiechanglei.lan.lang.string.StringHelper;

import java.lang.reflect.Field;

/**
 * FieldHelper 关于字段的工具类
 */
public class FieldHelper {
    /**
     * 获取字段的值
     */
    public static Object getValue(Object obj, String fieldName) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            return declaredField.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    public static void setValue(Object obj, String fieldName, Object value) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldName);
            declaredField.setAccessible(true);
            declaredField.set(obj, StringHelper.convert(value.toString(), declaredField.getType()));
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }

}
