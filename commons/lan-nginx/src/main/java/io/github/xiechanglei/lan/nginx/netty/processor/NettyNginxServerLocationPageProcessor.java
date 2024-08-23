package io.github.xiechanglei.lan.nginx.netty.processor;


import io.github.xiechanglei.lan.nginx.DefaultConfig;
import io.github.xiechanglei.lan.nginx.common.MediaTypeFactory;
import io.github.xiechanglei.lan.nginx.config.NginxLocation;
import io.github.xiechanglei.lan.nginx.netty.WrappedNettyContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.nio.file.Files;

/**
 * Location的静态资源页面处理器
 */
@Log4j2
public class NettyNginxServerLocationPageProcessor implements NettyNginxServerLocationProcessor {

    @Override
    public void process(WrappedNettyContext wrappedNettyContext) throws Exception {
        //page 模式下 只处理GET请求
        if (!wrappedNettyContext.getRequest().method().equals(HttpMethod.GET)) {
            wrappedNettyContext.writeErrorResponse(HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        // 处理请求
        String filePath = getFilePath(wrappedNettyContext.getRequest().uri(), wrappedNettyContext.getNginxLocation());
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            log.error("文件不存在:{}", filePath);
            wrappedNettyContext.writeErrorResponse(HttpResponseStatus.NOT_FOUND);
        } else {
            log.info("写入文件:{}", filePath);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(wrappedNettyContext.getRequest().protocolVersion(), HttpResponseStatus.OK);
            // 根据文件类型设置响应头，获取编码
            response.headers().set("Content-Type", MediaTypeFactory.getMediaType(file.getName(), wrappedNettyContext.getCharset()));
            // 写入文件内容
            response.content().writeBytes(Files.readAllBytes(file.toPath()));
            // 写入完整的响应
            wrappedNettyContext.writeFullHttpResponse(response);
// todo 文件太大，需要分块处理

//            FileChannel fileChannel = raf.getChannel();
//            ChunkedNioFile chunkedFile = new ChunkedNioFile(fileChannel);
//
//// Write the file content
//            ctx.write(chunkedFile);
//            try (RandomAccessFile raf = new RandomAccessFile(file, "r");
//                 FileChannel fileChannel = raf.getChannel()) {
//                // Write the file content
//                ctx.flush();
//```    ctx.write(new ChunkedNioFile(fileChannel));
//            } catch (IOException e) {
//                log.error("Error while reading the file", e);
//                ctx.write(new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.INTERNAL_SERVER_ERROR));
//            }


        }

    }


    /**
     * 获取文件路径
     * todo 是否需要考前缀处理
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
     * 检查配置 相关配置参数
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
        // index如果为空，则使用模式配置
        if (StringUtil.isNullOrEmpty(nginxLocation.getIndex())) {
            nginxLocation.setIndex(DefaultConfig.DEFAULT_INDEX_FILE);
        }
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
