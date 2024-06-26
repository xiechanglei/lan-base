package io.github.xiechanglei.lan.base.web.log;

import java.util.Map;

/**
 * 统一接口日志处理
 */
public interface LanBaseApiLogHandler {
    /**
     * 处理日志
     *
     * @param name   接口名称
     * @param path   接口路径
     * @param params 标记的参数
     */
    void handle(String name, String ip, String path, Map<String, Object> params) throws Exception;
}
