package io.github.xiechanglei.lan.web.log;

import java.util.Map;

/**
 * 统一接口日志处理器，对于标注了{@link ApiLog}的接口，会调用该处理器处理日志，可以存在多个处理器的实现，
 * rbac项目中已经实现了一个处理器，将数据存入数据库中
 */
public interface LanApiLogHandler {
    /**
     * 处理日志
     *
     * @param name   接口名称
     * @param path   接口路径
     * @param params 标记的参数
     */
    void handle(String name, String ip, String path, Map<String, Object> params);
}
