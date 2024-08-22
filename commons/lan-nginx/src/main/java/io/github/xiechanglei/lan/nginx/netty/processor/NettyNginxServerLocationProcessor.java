package io.github.xiechanglei.lan.nginx.netty.processor;

import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.github.xiechanglei.lan.nginx.netty.WrappedNettyContext;

/**
 * location处理器
 */
public interface NettyNginxServerLocationProcessor {
    /**
     * 处理请求
     *
     * @param wrappedNettyContext 上下文
     */
    void process(WrappedNettyContext wrappedNettyContext);

    /**
     * 检查配置 是否合法
     *
     * @param nginxLocation location
     * @throws IllegalArgumentException 配置不合法
     */
    void checkConfig(NginxLocation nginxLocation) throws IllegalArgumentException;
}
