package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.common.StaticsInfo;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.log4j.Log4j2;

/**
 * Netty Nginx 连接处理器
 */
@Log4j2
public class NettyNginxServerInitializer extends ChannelInitializer<SocketChannel> {

    // Http消息处理器
    private final NettyNginxServerHttpMessageProcessor httpMessageProcessor;

    public NettyNginxServerInitializer(NginxConfig nginxConfig, StaticsInfo staticsInfo) {
        this.httpMessageProcessor = new NettyNginxServerHttpMessageProcessor(nginxConfig, staticsInfo);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpServerCodec());
        p.addLast(httpMessageProcessor);
    }
}
