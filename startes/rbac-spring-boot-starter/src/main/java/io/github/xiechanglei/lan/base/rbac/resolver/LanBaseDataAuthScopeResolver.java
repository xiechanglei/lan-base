package io.github.xiechanglei.lan.base.rbac.resolver;

import io.github.xiechanglei.lan.base.rbac.annotation.AuthScope;
import io.github.xiechanglei.lan.base.rbac.custorm.DataAuthScope;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseDataAuthScopeResolver implements HandlerMethodArgumentResolver, ApplicationContextAware {

    private final Map<String, DataAuthScope> dataAuthScopeMap = new HashMap<>();
    private ApplicationContext applicationContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthScope.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        AuthScope authScope = parameter.getParameterAnnotation(AuthScope.class);
        if (authScope == null) {
            return null;
        }
        DataAuthScope bean = getDataAuthScope(authScope.value());
        return bean.buildScopeParam();
    }

    private DataAuthScope getDataAuthScope(Class<? extends DataAuthScope> dataAuthScopeClass) {
        return dataAuthScopeMap.computeIfAbsent(dataAuthScopeClass.getName(), key -> {
            try {
                return applicationContext.getBean(dataAuthScopeClass);
            } catch (BeansException e) {
                log.info("未从spring 容器中找到数据权限范围解析器:{}", dataAuthScopeClass.getName());
                try {
                    log.info("尝试自行实例化数据权限范围解析器:{}", dataAuthScopeClass.getName());
                    return dataAuthScopeClass.newInstance();
                } catch (Exception ex) {
                    throw new RuntimeException("数据权限范围解析器实例化失败");
                }
            }

        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
