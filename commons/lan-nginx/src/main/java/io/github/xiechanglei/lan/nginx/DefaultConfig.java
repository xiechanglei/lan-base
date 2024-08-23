package io.github.xiechanglei.lan.nginx;

import io.github.xiechanglei.lan.nginx.config.NginxLocationModel;
import io.github.xiechanglei.lan.nginx.config.NginxLocationRule;

public class DefaultConfig {

    /**
     * 服务的默认名称,显示response的header 当中 todo,做到
     */
    public static final String DEFAULT_SERVER_NAME = "lan-server";

    /**
     * 默认是否GZIP todo 未实现
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
    public static final String MIME_RESOURCE_PATH = "/lan-nginx-mime.types";

    /**
     * 默认的location匹配规则
     */
    public static final NginxLocationRule DEFAULT_LOCATION_RULE = NginxLocationRule.COMMON;

    /**
     * 默认的location处理模式
     */
    public static final NginxLocationModel DEFAULT_LOCATION_MODEL = NginxLocationModel.PAGE;

    /**
     * 超过此大小的文件，将会被分块传输
     */
    public static final int CHUNK_FILE_LIMIT = 1024 * 1024;

    /**
     * chunk size
     */
    public static final int CHUNK_SIZE = 10 * 1024;
}

