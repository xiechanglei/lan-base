package io.github.xiechanglei.lan.web.resolver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * spring boot配置参数类， 可自定义相关配置
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "lan.web.resolver")
public class LanResolverConfigProperties implements WebMvcConfigurer {

    /**
     * 是否开启全局日期解析，默认为true表示开启
     */
    private boolean date = true;

    /**
     * 是否开启全局分页解析，默认为true表示开启
     */
    private boolean page = true;

    /**
     * 分页参数名称
     */
    private String pageNoName = "pageNo";

    /**
     * 默认页码
     */
    private int pageNoDefault = 1;

    /**
     * 每页显示的记录数参数名称
     */
    private String pageSizeName = "pageSize";

    /**
     * 默认每页显示的记录数
     */
    private int pageSizeDefault = 20;
}
