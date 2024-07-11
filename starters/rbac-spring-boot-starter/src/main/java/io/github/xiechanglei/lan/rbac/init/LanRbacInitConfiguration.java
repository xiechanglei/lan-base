package io.github.xiechanglei.lan.rbac.init;

import io.github.xiechanglei.lan.rbac.init.data.LanRbacDataInitiation;
import io.github.xiechanglei.lan.rbac.properties.LanRbacConfigProperties;
import io.github.xiechanglei.lan.rbac.resolver.LanCurrentUserTypeResolver;
import io.github.xiechanglei.lan.rbac.resolver.LanDataAuthScopeResolver;
import io.github.xiechanglei.lan.rbac.resolver.LanPasswordTypeResolver;
import io.github.xiechanglei.lan.rbac.resolver.LanUserTypeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 数据初始化配置
 * TODO init 流程优化,系统部署之后大部分情况下不需要初始化权限数据
 */
@RequiredArgsConstructor
@Configuration
@Log4j2
public class LanRbacInitConfiguration implements ApplicationContextAware, WebMvcConfigurer {
    private final LanRbacConfigProperties lanRbacConfigProperties;
    private final LanRbacDataInitiation lanRbacDataInitiation;

    private final LanPasswordTypeResolver lanPasswordTypeResolver;
    private final LanCurrentUserTypeResolver lanCurrentUserTypeResolver;
    private final LanUserTypeResolver lanUserTypeResolver;
    private final LanDataAuthScopeResolver lanDataAuthScopeResolver;


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        log.info("开启RBAC权限控制");
        if (lanRbacConfigProperties.isEnable()) {
            lanRbacDataInitiation.initData(applicationContext);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(lanPasswordTypeResolver);
        resolvers.add(lanCurrentUserTypeResolver);
        resolvers.add(lanUserTypeResolver);
        if (lanRbacConfigProperties.isFilterData()) {
            resolvers.add(lanDataAuthScopeResolver);
        }
    }
}

