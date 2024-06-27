package io.github.xiechanglei.lan.json;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JsonContainerAdapter implements JsonContainer {
    private DocumentContext documentContext;

    public abstract String body();

    /**
     * to Object with path
     * convertToJson(User.class, "data.user")
     */
    public <T> T readJson(String path, Class<T> clazz) {
        if (this.documentContext == null) {
            this.documentContext = JsonPath.parse(body());
        }
        return documentContext.read(path, clazz);
    }

}
