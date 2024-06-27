package io.github.xiechanglei.lan.base.http;

import io.github.xiechanglei.lan.base.json.JsonContainerAdapter;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

public class HttpResponse extends JsonContainerAdapter {

    private final Connection.Response response;
    private boolean hasRead = false;
    private Document document;
    private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

    public HttpResponse(Connection.Response response) {
        this.response = response;
    }

    // header
    public String header(String name) {
        return response.header(name);
    }

    public Map<String, String> headers() {
        return response.headers();
    }

    public String cookie(String name) {
        return response.cookie(name);
    }

    public Map<String, String> cookies() {
        return response.cookies();
    }

    public int statusCode() {
        return response.statusCode();
    }

    public String charset() {
        return response.charset();
    }

    public String contentType() {
        return response.contentType();
    }

    //dom
    public Document parse() throws IOException {
        hasRead = true;
        if (document == null) {
            document = response.parse();
        }
        return document;
    }

    public Elements select(String query) throws IOException {
        return this.parse().select(query);
    }

    public String body() {
        hasRead = true;
        return response.body();
    }

    public BufferedInputStream bodyStream() {
        return response.bodyStream();
    }

    public byte[] bodyAsBytes() {
        return response.bodyAsBytes();
    }

    public File save(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        if (hasRead) {
            Files.write(file.toPath(), response.bodyAsBytes());
        } else {
            try (BufferedInputStream inputStream = response.bodyStream();
                 OutputStream OutputStream = Files.newOutputStream(file.toPath())) {
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int read;
                while ((read = inputStream.read(buffer, 0, DEFAULT_BUFFER_SIZE)) >= 0) {
                    OutputStream.write(buffer, 0, read);
                }
            }
        }
        return file;
    }

}
