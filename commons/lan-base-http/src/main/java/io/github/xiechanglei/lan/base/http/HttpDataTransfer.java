package io.github.xiechanglei.lan.base.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.xiechanglei.lan.base.json.JsonHelper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * http 数据传输对象
 */
@Getter
public class HttpDataTransfer {
    private final Map<String, String> headers = new HashMap<>();
    private final Map<String, String> params = new HashMap<>();
    private final Map<String, String> cookies = new HashMap<>();
    private String body;

    //header
    public HttpDataTransfer header(String key, Object value) {
        if (value != null) {
            headers.put(key, value.toString());
        } else {
            headers.remove(key);
        }
        return this;
    }

    public HttpDataTransfer contentType(String contentType) {
        header("Content-Type", contentType);
        return this;
    }

    //cookie
    public HttpDataTransfer cookie(String key, Object value) {
        if (value != null) {
            cookies.put(key, value.toString());
        } else {
            cookies.remove(key);
        }
        return this;
    }
    public HttpDataTransfer cookie(Map<String, String> cookies) {
        if (cookies != null) {
            this.cookies.putAll(cookies);
        }
        return this;
    }
    public HttpDataTransfer cookie(String cookieStr){
        if (cookieStr != null) {
            String[] split = cookieStr.split(";");
            for (String s : split) {
                String[] kv = s.split("=");
                if (kv.length == 2) {
                    cookie(kv[0], kv[1]);
                }
            }
        }
        return this;
    }

    //data
    public HttpDataTransfer param(String key, Object value) {
        if (value != null) {
            params.put(key, value.toString());
        } else {
            params.remove(key);
        }
        return contentType(HttpContentType.FORM);
    }

    //body request
    public HttpDataTransfer body(String body) {
        this.body = body;
        return this;
    }

    //json request
    public boolean isJson() {
        String s = headers.get("Content-Type");
        return s != null && s.toLowerCase().startsWith(HttpContentType.JSON);
    }

    public HttpDataTransfer json(Object data) {
        if (data != null) {
            try {
                body(JsonHelper.toJson(data));
            } catch (JsonProcessingException ignored) {
            }
        }
        return contentType(HttpContentType.JSON);
    }
}
