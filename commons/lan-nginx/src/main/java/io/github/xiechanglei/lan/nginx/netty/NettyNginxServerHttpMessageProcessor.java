package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.common.StaticsInfo;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.LastHttpContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Netty Nginx Http消息处理器
 */

@Log4j2
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyNginxServerHttpMessageProcessor extends SimpleChannelInboundHandler<HttpObject> {
    private final NginxConfig nginxConfig;
    private final StaticsInfo staticsInfo;

    /**
     * todo 应该自定义一个encoder，然后添加一写header信息,添加一些默认的header信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if (msg instanceof HttpRequest httpRequest) {
            // 处理正常的http请求
            log.info("收到请求:{}", httpRequest.uri());
            staticsInfo.addCurrentRequest();
            WrappedNettyContext wrappedNettyContext = WrappedNettyContext.of(ctx, httpRequest, nginxConfig);
            try {
                wrappedNettyContext.process();
            } catch (Exception e) {
                log.error("处理请求失败:{}", e.getMessage());
                wrappedNettyContext.writeErrorResponse(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            }
            staticsInfo.subCurrentRequest();
        } else if (msg instanceof LastHttpContent lastContent) {
            // 处理请求结束的消息
            log.info("收到请求结束");
            lastContent.release();
        } else {
            // 不支持的消息类型
            log.error("不支持的消息类型:{}", msg.getClass().getName());
            ctx.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        staticsInfo.addCurrentConnection();
        log.info("连接建立:{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        staticsInfo.subCurrentConnection();
        log.info("连接关闭:{}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("发生异常:{}", cause.getMessage());
        ctx.close();
    }
}