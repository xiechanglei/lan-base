package io.github.xiechanglei.lan.nginx;

import io.github.xiechanglei.lan.nginx.config.NginxLocationModel;
import io.github.xiechanglei.lan.nginx.config.NginxLocationRule;

/**
 * 一些默认的配置参数
 */

public class DefaultConfig {

    /**
     * 服务的默认名称,显示response的header当中
     */
    public static final String DEFAULT_SERVER_NAME = "lan-server";

    /**
     * 是否开启GZIP，默认为true，可以通过配置类修改
     */
    public static final boolean DEFAULT_GZIP = true;

    /**
     * 默认的index文件
     */
    public static final String DEFAULT_INDEX_FILE = "index.html";

    /**
     * 默认字符集
     */
    public static final String DEFAULT_CHARSET = "utf-8";

    /**
     * 没有任何匹配规则的mime类型
     */
    public static final String DEFAULT_MEDIA_TYPE = "application/octet-stream";

    /**
     * 默认提供的mime类型文件路径
     */
    public static final String DEFAULT_MIME_RESOURCE_PATH = "/lan-nginx-mime.types";

    /**
     * 默认的location匹配规则
     */
    public static final NginxLocationRule DEFAULT_LOCATION_RULE = NginxLocationRule.COMMON;

    /**
     * 默认的location处理模式
     */
    public static final NginxLocationModel DEFAULT_LOCATION_MODEL = NginxLocationModel.PAGE;

    /**
     * 静态资源服务器中，超过此大小的文件，将会被分块传输
     */
    public static final int DEFAULT_CHUNK_FILE_LIMIT = 10 * 1024 * 1024;

    /**
     * chunk size
     */
    public static final int DEFAULT_CHUNK_SIZE = 100 * 1024;

    /**
     * cache enable
     */
    public static final boolean DEFAULT_CACHE_ENABLE = true;

    /**
     * cache control time
     */
    public static final int DEFAULT_CACHE_CONTROL_TIME = 3600;
}

