package io.github.xiechanglei.lan.nginx.config;

import io.github.xiechanglei.lan.nginx.netty.processor.NettyNginxServerLocationPageProcessor;
import io.github.xiechanglei.lan.nginx.netty.processor.NettyNginxServerLocationProcessor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模拟Nginx的location配置中的模式，包括以下几种：
 * 1. PAGE: 页面模式，即返回页面
 * 2. API: 接口模式，即返回接口数据
 */
@Getter
@AllArgsConstructor
public enum NginxLocationModel {
    PAGE(new NettyNginxServerLocationPageProcessor()),
    API(null);

    private final NettyNginxServerLocationProcessor locationProcessor;

}
