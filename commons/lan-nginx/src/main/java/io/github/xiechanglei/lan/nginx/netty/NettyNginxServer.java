package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.NginxServer;
import io.github.xiechanglei.lan.nginx.common.StaticsInfo;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * 基于Netty实现的Nginx服务器
 */
@Log4j2
@RequiredArgsConstructor
public class NettyNginxServer implements NginxServer {
    // Nginx配置
    private final NginxConfig nginxConfig;

    // nginx 服务器的bossGroup和workerGroup  TODO 这里考虑以下优雅关闭
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    // 统计对象
    @Getter
    private StaticsInfo staticsInfo;

    @Override
    public void start() throws InterruptedException {
        log.info("启动Nginx服务器,port:{}", nginxConfig.getPort());
        // 创建bossGroup boosGroup表示
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 创建workerGroup,workerGroup的大小由配置文件决定
        EventLoopGroup workerGroup = new NioEventLoopGroup(nginxConfig.getWorkerGroup());

        staticsInfo = new StaticsInfo(nginxConfig.getPort());
        // 设置相关参数
        new ServerBootstrap().group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyNginxServerInitializer(nginxConfig,staticsInfo))
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .bind(nginxConfig.getPort()).sync();
        log.info("Nginx服务器启动成功");
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
    }


    public void stop() {
        log.info("关闭Nginx服务器");
        if (bossGroup != null && !bossGroup.isShutdown()) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }

}
