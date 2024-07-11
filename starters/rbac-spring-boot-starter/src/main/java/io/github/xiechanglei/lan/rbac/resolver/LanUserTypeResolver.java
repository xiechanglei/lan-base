package io.github.xiechanglei.lan.rbac.resolver;


import io.github.xiechanglei.lan.rbac.annotation.User;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.service.LanSysUserAuthService;
import lombok.RequiredArgsConstructor;
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
public class LanUserTypeResolver implements HandlerMethodArgumentResolver {
    private final LanSysUserAuthService lanSysUserAuthService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String id = webRequest.getParameter(Objects.requireNonNull(parameter.getParameterAnnotation(User.class)).value());
        return lanSysUserAuthService.findById(id).orElseThrow(() -> BusinessError.USER.USER_NOT_FOUND);
    }
}
