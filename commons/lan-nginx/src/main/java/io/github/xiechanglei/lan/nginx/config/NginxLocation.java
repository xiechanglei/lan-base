package io.github.xiechanglei.lan.nginx.config;

import io.github.xiechanglei.lan.nginx.netty.WrappedNettyContext;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 模拟配置Nginx的location，location支持以下配置：
 */
@Builder
@Getter
@Setter
public class NginxLocation {
    // 匹配规则
    private NginxLocationRule rule;

    // ruleValue
    @Builder.Default
    private String ruleValue = "";

    // location模式
    private NginxLocationModel model;

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
     * @param wrappedNettyContext 上下文
     */
    public void process(WrappedNettyContext wrappedNettyContext) {
        model.getLocationProcessor().process(wrappedNettyContext);
    }

    public void checkConfig() {
        try {
            model.getLocationProcessor().checkConfig(this);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("location(" + this.getRule().getRule() + " " + this.getRuleValue() + ")配置不合法: " + e.getMessage());
        }
    }

    // root
    private String root;

    // index
    private String index;

}
