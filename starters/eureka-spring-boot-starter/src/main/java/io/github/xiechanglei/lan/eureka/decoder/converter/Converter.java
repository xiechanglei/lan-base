package io.github.xiechanglei.lan.eureka.decoder.converter;

import io.github.xiechanglei.lan.json.JsonContainer;

import java.lang.reflect.Type;

/**
 * 转换器
 */
public interface Converter {
    Object convert(JsonContainer jsonContainer, Type type);

    boolean support(Type type);
}
