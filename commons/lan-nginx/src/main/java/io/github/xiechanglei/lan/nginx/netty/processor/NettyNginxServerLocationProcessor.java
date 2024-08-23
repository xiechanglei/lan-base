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
     * @param wrappedNettyContext 封装的请求上下文对象
     * @throws Exception 异常,如果抛出了异常,表示处理过程中没有去处理异常,需要调用者处理,返回错误响应
     */
    void process(WrappedNettyContext wrappedNettyContext) throws Exception;

    /**
     * 检查配置 是否合法
     *
     * @param nginxLocation location
     * @throws IllegalArgumentException 配置不合法
     */
    void checkConfig(NginxLocation nginxLocation) throws IllegalArgumentException;
}
