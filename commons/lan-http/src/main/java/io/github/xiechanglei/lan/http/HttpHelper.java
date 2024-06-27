package io.github.xiechanglei.lan.http;

import java.io.IOException;

/**
 * Http请求工具类，提供了一些常用的请求构建方法
 */
public class HttpHelper {
    /**
     * 构建一个请求，无任何默认配置
     */
    public static HttpRequest build() {
        return new HttpRequest();
    }

    /**
     * 构建一个请求，模拟浏览器的请求头
     */
    public static HttpRequest buildBrowserClient() {
        return new HttpRequest().payload(trans -> trans.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36"));
    }

    /**
     * 根据url发起get请求，返回响应
     */
    public static HttpResponse get(String url) throws IOException {
        return build().get(url);
    }

    /**
     * 模拟浏览器发起一个get请求，返回响应
     */
    public static HttpResponse browserGet(String url) throws IOException {
        return buildBrowserClient().get(url);
    }
}
