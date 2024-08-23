package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.http.HttpHelper;
import io.github.xiechanglei.lan.http.HttpResponse;
import io.github.xiechanglei.lan.nginx.NginxServer;

import java.io.IOException;

public class TestApplication {
    public static void main(String[] args) throws InterruptedException, IOException {
        startServer();
        Thread.sleep(1000);
//        sendRequest();
    }

    public static void startServer() throws InterruptedException {
        NginxServer.create("/home/xie/netty/html", 8080).start();
    }

    public static void sendRequest() throws IOException {
        HttpResponse response = HttpHelper.build().post("http://localhost:8080/index.html");
        System.out.println(response.body());
    }
}
