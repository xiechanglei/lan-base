package io.github.xiechanglei.lan.base.rbac.resolver;


import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.utils.string.StringOptional;
import io.github.xiechanglei.lan.base.rbac.annotation.Password;
import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.rbac.provide.RbacEncodeProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * HandlerMethodArgumentResolver是Spring MVC中用于处理控制器方法参数的接口。你可以通过实现这个接口来自定义参数解析逻辑。
 * 要使这个自定义的参数解析器生效，你需要将其注册到Spring MVC的参数解析器列表中。
 * 这可以通过实现WebMvcConfigurer接口并覆盖addArgumentResolvers方法来完成
 */
@Component
@RequiredArgsConstructor
public class LanBasePasswordTypeResolver implements HandlerMethodArgumentResolver {
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;
    private Pattern passwordPattern = null;

    /**
     * 优化正则表达式生成过程
     */
    @PostConstruct
    public void init() {
        StringOptional.of(lanBaseRbacConfigProperties.getPasswordRule())
                .ifPresent(rule -> passwordPattern = Pattern.compile(rule));
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Password.class);
    }

    /**
     * 获取参数的值，并做加密处理
     */
    @Override
    public String resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String parameterValue = webRequest.getParameter(Objects.requireNonNull(parameter.getParameterName()));
        Password password = parameter.getParameterAnnotation(Password.class);
        if (parameterValue == null) {
            throw BusinessException.of("密码不能为空");
        }

        assert password != null;
        if (passwordPattern != null && password.check() && !passwordPattern.matcher(parameterValue).matches()) {
            throw BusinessException.of("密码不符合规则");
        }
        return RbacEncodeProcessor.encode(parameterValue);
    }
}
