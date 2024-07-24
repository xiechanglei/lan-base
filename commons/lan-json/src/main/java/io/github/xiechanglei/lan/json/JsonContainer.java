package io.github.xiechanglei.lan.json;

import com.jayway.jsonpath.TypeRef;
import io.github.xiechanglei.lan.lang.reflect.SyntheticParameterizedType;

import java.util.Map;
import java.util.List;
import java.lang.reflect.Type;


public interface JsonContainer {
    <T> T readJson(String path, Class<T> clazz);

    <T> T readJson(String path, TypeRef<T> typeRef);

    default <T> List<T> readList(String path, Class<T> clazz) {
        return this.readJson(path, new TypeRef<>() {
            @Override
            public Type getType() {
                return SyntheticParameterizedType.of(List.class, clazz);
            }
        });
    }

    /**
     * toMap
     */
    @SuppressWarnings("unchecked")
    default Map<String, Object> readJson() {
        return this.readJson("$", Map.class);
    }

    /**
     * to Object
     */
    default <T> T readJson(Class<T> clazz) {
        return this.readJson("$", clazz);
    }

    /**
     * to Object
     */
    default <T> T readJson(TypeRef<T> typeRef) {
        return this.readJson("$", typeRef);
    }

}
