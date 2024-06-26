package io.github.xiechanglei.lan.base.rbac.resolver;


import io.github.xiechanglei.lan.base.rbac.annotation.User;
import io.github.xiechanglei.lan.base.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LanBaseUserTypeResolver implements HandlerMethodArgumentResolver {
    private final LanBaseSysUserAuthService lanBaseSysUserAuthService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String id = webRequest.getParameter(Objects.requireNonNull(parameter.getParameterAnnotation(User.class)).value());
        return lanBaseSysUserAuthService.findById(id).orElseThrow(() -> BusinessError.USER.USER_NOT_FOUND);
    }
}
