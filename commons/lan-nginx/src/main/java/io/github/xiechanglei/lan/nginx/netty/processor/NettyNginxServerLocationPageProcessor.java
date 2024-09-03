package io.github.xiechanglei.lan.nginx.netty.processor;


import io.github.xiechanglei.lan.lang.bytecode.ByteArrayHelper;
import io.github.xiechanglei.lan.lang.date.DateHelper;
import io.github.xiechanglei.lan.lang.date.DateParseInterface;
import io.github.xiechanglei.lan.nginx.DefaultConfig;
import io.github.xiechanglei.lan.nginx.common.MediaTypeFactory;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.github.xiechanglei.lan.nginx.netty.WrappedNettyContext;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

/**
 * Location的静态资源页面处理器
 */
@Log4j2
public class NettyNginxServerLocationPageProcessor implements NettyNginxServerLocationProcessor {

    private DateParseInterface.DateConverter dateConverter = DateHelper.DEFAULT_DATETIME_MS_PARSER;

    @Override
    public void process(String pureUri, WrappedNettyContext wrappedNettyContext) throws Exception {
        //page 模式下 只处理GET请求
        if (!wrappedNettyContext.request.method().equals(HttpMethod.GET)) {
            wrappedNettyContext.writeErrorResponse(HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        // 真实文件路径
        String filePath = getFilePath(pureUri, wrappedNettyContext.nginxLocation);
        // 处理请求
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            // 文件不存在，返回404
            log.error("file not found:{}", filePath);
            wrappedNettyContext.writeErrorResponse(HttpResponseStatus.NOT_FOUND);
        } else {
            // 处理cache
            if (isCached(wrappedNettyContext, file)) {
                String contentType = MediaTypeFactory.getMediaType(file.getName(), wrappedNettyContext.getCharset());
                wrappedNettyContext.writeErrorResponse(HttpResponseStatus.NOT_MODIFIED, contentType);
                return;
            }
            // 写入文件
            log.info("write file:{}", filePath);
            if (file.length() < DefaultConfig.DEFAULT_CHUNK_FILE_LIMIT) {
                writeNoChunkResponse(wrappedNettyContext, file);
            } else {
                writeChunkResponse(wrappedNettyContext, file);
            }
        }
    }

    /**
     * 判断是否使用缓存
     *
     * @param wrappedNettyContext 上下文
     * @param file                文件
     * @return 是否缓存
     */
    public boolean isCached(WrappedNettyContext wrappedNettyContext, File file) {
        // 如果不支持缓存，则直接返回false
        if (!wrappedNettyContext.getCacheEnable()) {
            return false;
        }
        // 获取请求头中的If-Modified-Since字段
        String ifModifiedSince = wrappedNettyContext.request.headers().get("If-Modified-Since");
        // 如果请求头中的If-Modified-Since字段为空，则返回false
        if (StringUtil.isNullOrEmpty(ifModifiedSince)) {
            return false;
        }
        // 获取文件的最后修改时间
        long lastModified = file.lastModified();
        // 如果请求头中的If-Modified-Since字段不为空，且文件的最后修改时间小于请求头中的If-Modified-Since字段，则返回true
        try {
            return lastModified == dateConverter.parse(ifModifiedSince).getTime();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 大文件使用chunk分块响应
     *
     * @param wrappedNettyContext 上下文
     * @param file                文件
     * @throws IOException IO异常
     */
    private void writeChunkResponse(WrappedNettyContext wrappedNettyContext, File file) throws IOException {
        DefaultHttpResponse response = new DefaultHttpResponse(wrappedNettyContext.request.protocolVersion(), HttpResponseStatus.OK);
        // 根据文件类型设置响应头，设置编码以及cache
        dealContentTypeAndCache(response, file, wrappedNettyContext);
        // Transfer-Encoding和Content-Length是互斥的，不会同时出现在相同的HTTP响应中
        response.headers().set("Transfer-Encoding", "chunked");
        // 但是这个字段对下载是有用的，可以让浏览器知道文件的大小
        response.headers().set("Content-Length", file.length());
        // 写入响应头
        wrappedNettyContext.writeHttpResponse(response);
        // 写入文件
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[DefaultConfig.DEFAULT_CHUNK_SIZE];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                DefaultHttpContent content = new DefaultHttpContent(Unpooled.wrappedBuffer(bytes, 0, len));
                wrappedNettyContext.ctx.writeAndFlush(content);
            }
            wrappedNettyContext.ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        }

    }

    /**
     * 写入完整的响应,不分块，并且处理gzip压缩
     *
     * @param wrappedNettyContext 上下文
     * @param file                需要写入的文件
     * @throws IOException IO异常
     */
    public void writeNoChunkResponse(WrappedNettyContext wrappedNettyContext, File file) throws IOException {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(wrappedNettyContext.request.protocolVersion(), HttpResponseStatus.OK);
        // 根据文件类型设置响应头，设置编码以及cache
        dealContentTypeAndCache(response, file, wrappedNettyContext);
        // 读取文件
        byte[] bytes = Files.readAllBytes(file.toPath());
        // 如果支持gzip压缩，则进行压缩
        if (wrappedNettyContext.getGzipEnable()) {
            bytes = ByteArrayHelper.gzip(bytes);
            response.headers().set("Content-Encoding", "gzip");
        }
        response.content().writeBytes(bytes);
        // 写入完整的响应
        wrappedNettyContext.writeFullHttpResponse(response);
    }

    /**
     * 根据文件名设置Content-Type
     *
     * @param response            响应
     * @param file                文件
     * @param wrappedNettyContext 上下文
     */
    public void dealContentTypeAndCache(HttpResponse response, File file, WrappedNettyContext wrappedNettyContext) {
        response.headers().set("Content-Type", MediaTypeFactory.getMediaType(file.getName(), wrappedNettyContext.getCharset()));
        if (wrappedNettyContext.getCacheEnable()) {
            response.headers().set("Cache-Control", "max-age=" + wrappedNettyContext.nginxLocation.getCacheControlTime());
            response.headers().set("Last-Modified", dateConverter.format(new Date(file.lastModified())));
        }
    }


    /**
     * 获取文件路径
     * todo 需要考前缀处理
     *
     * @param uri           请求地址
     * @param nginxLocation location配置
     * @return 文件路径
     */
    private String getFilePath(String uri, NginxLocation nginxLocation) {
        if (uri.endsWith("/")) {
            uri += nginxLocation.getIndex();
        }
        return nginxLocation.getRoot() + uri;
    }

    /**
     * 检查配置的相关配置参数是否合法
     */
    @Override
    public void checkConfig(NginxLocation nginxLocation) throws IllegalArgumentException {
        // root不能为空
        if (StringUtil.isNullOrEmpty(nginxLocation.getRoot())) {
            throw new IllegalArgumentException("root不能为空");
        }
        // 对root进行格式化.去掉末尾的/
        while (nginxLocation.getRoot().endsWith("/")) {
            nginxLocation.setRoot(nginxLocation.getRoot().substring(0, nginxLocation.getRoot().length() - 1));
        }
    }

}
