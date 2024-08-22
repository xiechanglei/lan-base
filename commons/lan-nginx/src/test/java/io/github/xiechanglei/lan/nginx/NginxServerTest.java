package io.github.xiechanglei.lan.nginx;

import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.github.xiechanglei.lan.nginx.config.NginxLocationModel;
import io.github.xiechanglei.lan.nginx.config.NginxLocationRule;

import java.util.Arrays;

/**
 * Nginx服务器测试类
 */
public class NginxServerTest {

    public static void main(String[] args) throws InterruptedException {
        // 前端页面location
        NginxLocation pageLocation = NginxLocation.builder()
                .rule(NginxLocationRule.COMMON)
                .model(NginxLocationModel.PAGE)
                .root("/home/xie/netty/html")
                .build();

        // 前端页面location
        NginxLocation testLocation = NginxLocation.builder()
                .rule(NginxLocationRule.PREFIX)
                .ruleValue("/test")
                .model(NginxLocationModel.PAGE)
                .root("/home/xie/netty/test")
                .build();

        // Nginx配置
        NginxConfig config = NginxConfig.builder()
                .port(8080)
                .locations(Arrays.asList(pageLocation, testLocation))
                .build();

        // 启动Nginx服务器
        NginxServer.create(config).start();
    }
}
