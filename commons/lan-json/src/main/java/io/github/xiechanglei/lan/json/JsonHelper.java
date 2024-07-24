package io.github.xiechanglei.lan.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.lang.reflect.Type;

public class JsonHelper {

    public static String toJson(Object obj) throws JsonProcessingException {
        return JacksonConfig.OBJECT_MAPPER.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return JacksonConfig.OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) throws JsonProcessingException {
        return JacksonConfig.OBJECT_MAPPER.readValue(json, JacksonConfig.OBJECT_MAPPER.constructType(type));
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return JacksonConfig.OBJECT_MAPPER.readValue(json, typeReference);
    }
}
