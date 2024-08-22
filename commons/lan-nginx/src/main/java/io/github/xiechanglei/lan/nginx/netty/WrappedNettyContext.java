package io.github.xiechanglei.lan.nginx.netty;

import io.github.xiechanglei.lan.nginx.config.NginxConfig;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Getter
@Log4j2
@RequiredArgsConstructor
public class WrappedNettyContext {
    private final ChannelHandlerContext ctx;
    private final HttpRequest request;
    private final NginxConfig nginxConfig;
    private NginxLocation nginxLocation;

    public void process() {
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
        // close 根据request的header决定是否关闭连接
        response.headers().set("Connection", isKeepAlive() ? "keep-alive" : "close");

        ChannelFuture channelFuture = ctx.writeAndFlush(response);
        if (!isKeepAlive()) {
            channelFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public boolean isKeepAlive() {
        return "keep-alive".equalsIgnoreCase(request.headers().get("Connection"));
    }

    /**
     * 创建WrappedNettyContext ，并处理请求
     *
     * @param ctx         netty上下文
     * @param request     请求
     * @param nginxConfig nginx配置
     */
    public static void of(ChannelHandlerContext ctx, HttpRequest request, NginxConfig nginxConfig) {
        new WrappedNettyContext(ctx, request, nginxConfig).process();
    }

//    /**
//     * todo
//     *
//     * @param data
//     * @return
//     * @throws IOException
//     */
//    public static byte[] gzip(byte[] data) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
//            gzipOutputStream.write(data);
//        }
//        return byteArrayOutputStream.toByteArray();
//    }
}
