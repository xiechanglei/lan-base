package io.github.xiechanglei.lan.nginx.netty;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * Netty Nginx 服务器响应写入器
 */
public class NettyNginxServerHttpResponseWriter {

    /**
     * 写入错误
     *
     * @param ctx     上下文
     * @param request 请求
     * @param status  状态
     */
    public static void writerError(ChannelHandlerContext ctx, HttpRequest request, HttpResponseStatus status) {

    }

    /**
     * 写入完整的响应
     *
     * @param ctx      上下文
     * @param response 响应
     */
    public static void writeFullHttpResponse(ChannelHandlerContext ctx, DefaultFullHttpResponse response) {
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
