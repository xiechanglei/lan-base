package io.github.xiechanglei.lan.nginx.config;

import io.github.xiechanglei.lan.nginx.DefaultConfig;
import io.github.xiechanglei.lan.nginx.netty.WrappedNettyContext;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static io.github.xiechanglei.lan.nginx.DefaultConfig.*;

/**
 * 模拟配置Nginx的location，location支持以下配置：
 */
@Builder
@Getter
@Setter
public class NginxLocation {
    /**
     * 匹配规则，默认为common匹配模式，类似于nginxl里面的
     * location / {
     * ...
     * }
     */
    @Builder.Default
    private NginxLocationRule rule = DefaultConfig.DEFAULT_LOCATION_RULE;

    /**
     * 匹配规则的值，如 "/test" 类似于nginx里面的：
     * location ^~ /test {
     * ...
     * }
     */
    @Builder.Default
    private String ruleValue = "/";

    /**
     * location处理模式
     */
    @Builder.Default
    private NginxLocationModel model = DefaultConfig.DEFAULT_LOCATION_MODEL;

    /**
     * location下的charset，高于全局配置的charset
     */
    private String charset;

    /**
     * location下的gzip配置，高于全局配置的gzip
     */
    private Boolean gzip;

    /**
     * 页面模式下的参数，表示页面的根目录
     */
    private String root;

    /**
     * 页面模式的参数，表示默认页面
     */
    @Builder.Default
    private String index = DEFAULT_INDEX_FILE;

    /**
     * 页面模式下的参数，表示是否使用缓存机制
     */
    @Builder.Default
    private boolean useCache = DEFAULT_CACHE_ENABLE;

    /**
     * 页面模式下的参数，表示缓存时间
     */
    @Builder.Default
    private int cacheControlTime = DEFAULT_CACHE_CONTROL_TIME;

    /**
     * 匹配uri
     *
     * @param uri uri
     * @return 是否匹配
     */
    public boolean match(String uri) {
        return rule.match(uri, ruleValue);
    }

    /**
     * 根据model获取响应结果
     *
     * @param pureUri             纯净的uri,去除了参数
     * @param wrappedNettyContext 上下文
     */
    public void process(String pureUri, WrappedNettyContext wrappedNettyContext) throws Exception {
        model.getLocationProcessor().process(pureUri, wrappedNettyContext);
    }

    /**
     * 检查配置是否合法
     */
    public void checkConfig() {
        // 检查模式下的配置是否合法
        try {
            model.getLocationProcessor().checkConfig(this);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("location(" + this.getRule().getRule() + " " + this.getRuleValue() + ")配置不合法: " + e.getMessage());
        }
    }

}
