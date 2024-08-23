package io.github.xiechanglei.lan.nginx.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpResponse;

import java.util.List;

/**
 * 自定义HttpResponse编码器，主要是添加一些头部信息
 * todo 报错了
 */
@ChannelHandler.Sharable
public class NettyServerHttpResponseEncoder extends MessageToMessageEncoder<HttpResponse> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, HttpResponse httpResponse, List<Object> list) {

    }
}
