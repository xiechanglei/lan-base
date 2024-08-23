package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.DefaultConfig;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
public class WrappedNettyContext {
    private final ChannelHandlerContext ctx;
    @Getter
    private final HttpRequest request;
    @Getter
    private final NginxConfig nginxConfig;
    @Getter
    private NginxLocation nginxLocation;

    public void process() throws Exception {
        log.info("收到请求:{}", request.uri());
        this.nginxLocation = nginxConfig.matchLocation(request.uri());
        if (nginxLocation == null) {
            log.error("未匹配到location:{}", request.uri());
            // 返回404
            NettyNginxServerHttpResponseWriter.writerError(ctx, request, HttpResponseStatus.NOT_FOUND);
        } else {
            // 处理http请求
            log.info("匹配到location:{}", nginxLocation.getRule().getRule() + " " + nginxLocation.getRuleValue());
            nginxLocation.process(this);
        }
    }

    /**
     * 根据http状态码写入错误响应
     *
     * @param status 状态
     */
    public void writeErrorResponse(HttpResponseStatus status) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), status);
        // 设置响应头 text plain
        response.headers().set("Content-Type", "text/plain; charset=UTF-8");
        // 设置响应体
        response.content().writeBytes(status.reasonPhrase().getBytes());
        // 写入完整的响应
        writeFullHttpResponse(response);
    }

    /**
     * 写入完整的响应
     *
     * @param response 响应
     */
    public void writeFullHttpResponse(FullHttpResponse response) {
        // 长度
        response.headers().set("Content-Length", response.content().readableBytes());

        ChannelFuture channelFuture = writeHttpResponse(response);
        if (!isKeepAlive()) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 写入http response
     * 并且做统一的header处理
     */
    public ChannelFuture writeHttpResponse(HttpResponse response) {
        // close 根据request的header决定是否关闭连接
        response.headers().set("Connection", isKeepAlive() ? "keep-alive" : "close");
        // server
        response.headers().set("Server", nginxConfig.getServerName());
        // date
        return ctx.writeAndFlush(response);
    }

    /**
     * 判断当前请求是否支持长连接
     */
    public boolean isKeepAlive() {
        return "keep-alive".equalsIgnoreCase(request.headers().get("Connection"));
    }

    /**
     * 获取编码配置
     */
    public String getCharset() {
        if (nginxLocation.getCharset() != null) {
            return nginxLocation.getCharset();
        }
        if (nginxConfig.getCharset() != null) {
            return nginxConfig.getCharset();
        }
        return DefaultConfig.DEFAULT_CHARSET;
    }

    /**
     * 获取是否开启gzip
     */
    public boolean getGzipEnable() {
        // 如果请求不支持gzip，直接返回false
        if (!request.headers().contains("Accept-Encoding") && request.headers().get("Accept-Encoding").contains("gzip")) {
            return false;
        }
        // 如果location配置了gzip，优先使用location配置
        if (nginxLocation.getGzip() != null) {
            return nginxLocation.getGzip();
        }

        // 如果全局配置了gzip，使用全局配置
        if (nginxConfig.getGzip() != null) {
            return nginxConfig.getGzip();
        }
        // 否则使用默认配置
        return DefaultConfig.DEFAULT_GZIP;
    }

    /**
     * 创建WrappedNettyContext
     *
     * @param ctx         netty上下文
     * @param request     请求
     * @param nginxConfig nginx配置
     * @return WrappedNettyContext 对象
     */
    public static WrappedNettyContext of(ChannelHandlerContext ctx, HttpRequest request, NginxConfig nginxConfig) {
        return new WrappedNettyContext(ctx, request, nginxConfig);
    }
}
