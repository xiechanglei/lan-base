package io.github.xiechanglei.lan.base.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.function.Consumer;

public class HttpRequest {
    private final HttpDataTransfer httpDataTransfer = new HttpDataTransfer();
    private int timeoutMillis = 30000;

    /**
     * 塞数据
     */
    public HttpRequest payload(Consumer<HttpDataTransfer> provider) {
        provider.accept(httpDataTransfer);
        return this;
    }

    public HttpRequest timeout(int timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
        return this;
    }

    //发请求获取内容
    public HttpResponse get(String url) throws IOException {
        return request(url, "GET");
    }

    public HttpResponse post(String url) throws IOException {
        return request(url, "POST");
    }

    public HttpResponse request(String url, String method) throws IOException {
        return parse(buildConnection(url).method(Connection.Method.valueOf(method)).execute());
    }
    //下载东西

    private HttpResponse parse(Connection.Response response) {
        return new HttpResponse(response);
    }

    private Connection buildConnection(String url) {
        Connection connection = Jsoup
                .connect(url)
                .cookies(httpDataTransfer.getCookies())
                .headers(httpDataTransfer.getHeaders())
                .timeout(timeoutMillis)
                .ignoreContentType(true);
        if (httpDataTransfer.isJson()) {
            connection.requestBody(httpDataTransfer.getBody());
        } else {
            connection.data(httpDataTransfer.getParams());
        }
        return connection;
    }
}
