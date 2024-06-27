package io.github.xiechanglei.lan.json;

import java.util.Map;

public interface JsonContainer {
    <T> T readJson(String path, Class<T> clazz);

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

}
