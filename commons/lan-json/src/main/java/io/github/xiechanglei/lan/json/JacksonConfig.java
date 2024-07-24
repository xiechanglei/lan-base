package io.github.xiechanglei.lan.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonConfig {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 忽略未知字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略transient属性
        OBJECT_MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
    }
}
