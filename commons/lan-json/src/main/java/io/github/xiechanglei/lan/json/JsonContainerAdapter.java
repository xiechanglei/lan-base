package io.github.xiechanglei.lan.json;


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.RequiredArgsConstructor;

/**
 * json Path实现
 */
@RequiredArgsConstructor
public abstract class JsonContainerAdapter implements JsonContainer {
    private DocumentContext documentContext;
    private static Configuration defaultsConfiguration = Configuration.builder()
            .jsonProvider(new JacksonJsonProvider(JacksonConfig.OBJECT_MAPPER))
            .mappingProvider(new JacksonMappingProvider(JacksonConfig.OBJECT_MAPPER))
            .build();

    public abstract String body();

    /**
     * to Object with path
     * convertToJson(User.class, "data.user")
     */
    public <T> T readJson(String path, Class<T> clazz) {
        checkParse();
        return documentContext.read(path, clazz);
    }

    /**
     * to Object with path
     * convertToJson(new TypeRef<List<User>>(){}, "data.user")
     */
    public <T> T readJson(String path, TypeRef<T> typeRef) {
        checkParse();
        return documentContext.read(path, typeRef);
    }

    /**
     * 判断是否已经解析
     */
    private void checkParse() {
        if (this.documentContext == null) {
            this.documentContext = JsonPath.parse(body(), defaultsConfiguration);
        }
    }



}
