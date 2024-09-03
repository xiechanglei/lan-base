package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.DefaultConfig;
import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RequiredArgsConstructor
public class WrappedNettyContext {
    public final ChannelHandlerContext ctx;
    public final HttpRequest request;
    public final NginxConfig nginxConfig;
    public NginxLocation nginxLocation;

    /**
     * 处理请求，根据请求的url去匹配location，然后根据location的处理模式进行处理
     */
    public void process() throws Exception {
        String pureUri = getPureUri();
        this.nginxLocation = nginxConfig.matchLocation(pureUri);
        if (nginxLocation == null) {
            log.error("unmatched location:{}", pureUri);
            // 返回404
            NettyNginxServerHttpResponseWriter.writerError(ctx, request, HttpResponseStatus.NOT_FOUND);
        } else {
            // 处理http请求
            nginxLocation.process(pureUri, this);
        }
    }

    /**
     * 根据http状态码写入错误响应
     *
     * @param status 状态
     */
    public void writeErrorResponse(HttpResponseStatus status) {
        writeErrorResponse(status, "text/plain; charset=UTF-8");
    }

    /**
     * 根据http状态码写入错误响应
     *
     * @param status      状态
     * @param contentType 响应类型
     */
    public void writeErrorResponse(HttpResponseStatus status, String contentType) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), status);
        // 设置响应头 text plain
        response.headers().set("Content-Type", contentType);
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

        writeHttpResponse(response);

    }

    /**
     * 写入http response
     * 并且做统一的header处理
     */
    public void writeHttpResponse(HttpResponse response) {
        // close 根据request的header决定是否关闭连接
        response.headers().set("Connection", isKeepAlive() ? "keep-alive" : "close");
        // server
        response.headers().set("Server", nginxConfig.getServerName());
        // date
        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        if (!isKeepAlive()) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 判断当前请求是否支持长连接
     */
    public boolean isKeepAlive() {
        return "keep-alive".equalsIgnoreCase(request.headers().get("Connection"));
    }

    /**
     * 获取纯净的uri
     */
    public String getPureUri() {
        return request.uri().split("\\?")[0];
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
        // 写入到location中
        nginxLocation.setCharset(DefaultConfig.DEFAULT_CHARSET);
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
        // 否则使用默认配置,并且将gzip的配置写入到location中
        nginxLocation.setGzip(DefaultConfig.DEFAULT_GZIP);
        return DefaultConfig.DEFAULT_GZIP;
    }

    /**
     * 判读是否开启缓存
     *
     * @return 是否开启缓存
     */
    public boolean getCacheEnable() {
        return nginxLocation.isUseCache();
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
