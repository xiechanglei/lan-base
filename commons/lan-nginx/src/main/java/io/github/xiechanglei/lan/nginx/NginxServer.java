package io.github.xiechanglei.lan.nginx;

import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.netty.NettyNginxServer;
import lombok.NonNull;

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
     * 启动服务
     */
    void start() throws InterruptedException;

    /**
     * 停止服务
     */

    void stop();
}
