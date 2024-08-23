package io.github.xiechanglei.lan.nginx;

import io.github.xiechanglei.lan.nginx.common.StaticsInfo;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.github.xiechanglei.lan.nginx.netty.NettyNginxServer;
import lombok.NonNull;

import java.util.Collections;

/**
 * 模拟一个nginx服务器，具体的参数配置参照{@link NginxConfig}
 */
public interface NginxServer {
    /**
     * 根据配置类创建一个nginx服务器
     *
     * @param nginxConfig nginx配置类
     * @return nginx服务器
     */
    static NginxServer create(@NonNull NginxConfig nginxConfig) {
        return new NettyNginxServer(nginxConfig.init());
    }


    /**
     * 快速在一个目录下创建一个nginx服务器，全部都是默认配置
     *
     * @param path 目录
     * @param port 端口
     * @return nginx服务器
     */
    static NginxServer create(String path, int port) {
        NginxLocation location = NginxLocation.builder().root(path).build();
        NginxConfig config = NginxConfig.builder().port(port).locations(Collections.singletonList(location)).build();
        return create(config);
    }

    /**
     * 启动服务
     */
    void start() throws InterruptedException;

    /**
     * 停止服务
     */
    void stop();

    /**
     * 获取统计信息
     */
    StaticsInfo getStaticsInfo();

}
