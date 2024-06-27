package io.github.xiechanglei.lan.web.resolver;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 注册常用的请求参数解析器
 */
@Log4j2
@Configuration
@RequiredArgsConstructor
public class LanResolverConfiguration implements WebMvcConfigurer {
    private final LanResolverConfigProperties lanResolverConfigProperties;
    private final LanDateResolver lanDateResolver;

    private final LanPageResolver lanPageResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        if (lanResolverConfigProperties.isDate()) {
            log.info("开启日期类型对象参数解析 (lan.web.resolver.date=true)...");
            resolvers.add(lanDateResolver);
        }
        if (lanResolverConfigProperties.isPage()) {
            log.info("开启PageParam类型对象参数解析 (lan.web.resolver.page=true)...");
            resolvers.add(lanPageResolver);
        }
    }
}
