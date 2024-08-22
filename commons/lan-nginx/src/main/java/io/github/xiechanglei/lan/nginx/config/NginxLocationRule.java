package io.github.xiechanglei.lan.nginx.config;

import lombok.Getter;

import java.util.regex.Pattern;

/**
 * 模拟配置Nginx的location中的匹配规则
 */
public enum NginxLocationRule {
    EQUAL("=", 1),
    // 前缀匹配
    PREFIX("^~", 2),
    // 区分大小写的正则匹配 优化算法，正则提前编译
    REGEX("~", 3),
    // 不区分大小写的正则匹配
    REGEX_IGNORE_CASE("~*", 4),
    // 区分大小写的正则不匹配
    NOT_REGEX("!~", 5),
    // 不区分大小写的正则不匹配
    NOT_REGEX_IGNORE_CASE("!~*", 6),
    // 通用匹配
    COMMON("/", 7);

    // 规则的字符串表示
    @Getter
    private final String rule;

    // 规则优先级
    @Getter
    private final int priority;

    // 预编译正则表达式
    private final Pattern pattern;

    NginxLocationRule(String rule, int priority) {
        this.rule = rule;
        this.priority = priority;
        this.pattern = Pattern.compile(rule);
    }

    /**
     * 匹配规则
     *
     * @param uri       请求的uri
     * @param ruleValue 规则值
     * @return 是否匹配
     */
    public boolean match(String uri, String ruleValue) {
        if (this == EQUAL) {
            return uri.equals(ruleValue);
        } else if (this == PREFIX) {
            return uri.startsWith(ruleValue);
        } else if (this == REGEX) {
            return pattern.matcher(uri).matches();
        } else if (this == REGEX_IGNORE_CASE) {
            uri = uri.toLowerCase();
            return pattern.matcher(uri).matches();
        } else if (this == NOT_REGEX) {
            return !pattern.matcher(uri).matches();
        } else if (this == NOT_REGEX_IGNORE_CASE) {
            uri = uri.toLowerCase();
            return !pattern.matcher(uri).matches();
        } else return this == COMMON;
    }
}
