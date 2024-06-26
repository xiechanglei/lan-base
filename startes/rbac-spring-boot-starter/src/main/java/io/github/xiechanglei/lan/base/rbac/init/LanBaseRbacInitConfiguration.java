package io.github.xiechanglei.lan.base.rbac.init;

import io.github.xiechanglei.lan.base.rbac.init.data.LanBaseRbacDataInitiation;
import io.github.xiechanglei.lan.base.rbac.init.table.LanBaseRbacTableInitiation;
import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.rbac.resolver.LanBaseCurrentUserTypeResolver;
import io.github.xiechanglei.lan.base.rbac.resolver.LanBasePasswordTypeResolver;
import io.github.xiechanglei.lan.base.rbac.resolver.LanBaseUserTypeResolver;
import io.github.xiechanglei.lan.base.rbac.resolver.LanBaseDataAuthScopeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 数据初始化配置
 */
@RequiredArgsConstructor
@Configuration
@Log4j2
public class LanBaseRbacInitConfiguration implements ApplicationContextAware, WebMvcConfigurer {
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;
    private final LanBaseRbacTableInitiation lanBaseRbacTableInitiation;
    private final LanBaseRbacDataInitiation lanBaseRbacDataInitiation;

    private final LanBasePasswordTypeResolver lanBasePasswordTypeResolver;
    private final LanBaseCurrentUserTypeResolver lanBaseCurrentUserTypeResolver;
    private final LanBaseUserTypeResolver lanBaseUserTypeResolver;
    private final LanBaseDataAuthScopeResolver lanBaseDataAuthScopeResolver;


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        log.info("开启RBAC权限控制");
        if (lanBaseRbacConfigProperties.isEnable()) {
            lanBaseRbacTableInitiation.createTableIfNotExist();
            lanBaseRbacDataInitiation.initData(applicationContext);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(lanBasePasswordTypeResolver);
        resolvers.add(lanBaseCurrentUserTypeResolver);
        resolvers.add(lanBaseUserTypeResolver);
        if (lanBaseRbacConfigProperties.isFilterData()) {
            resolvers.add(lanBaseDataAuthScopeResolver);
        }
    }
}

