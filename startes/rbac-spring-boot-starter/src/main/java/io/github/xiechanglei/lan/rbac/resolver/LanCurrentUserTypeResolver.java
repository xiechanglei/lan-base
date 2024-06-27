package io.github.xiechanglei.lan.rbac.resolver;


import io.github.xiechanglei.lan.rbac.annotation.CurrentUser;
import io.github.xiechanglei.lan.rbac.annotation.CurrentUserId;
import io.github.xiechanglei.lan.rbac.annotation.ParameterUser;
import io.github.xiechanglei.lan.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.rbac.provide.UserContextHolder;
import io.github.xiechanglei.lan.rbac.service.LanSysUserAuthService;
import io.github.xiechanglei.lan.utils.string.StringHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class LanCurrentUserTypeResolver implements HandlerMethodArgumentResolver {
    private final LanSysUserAuthService sysUserAuthService;
    private final UserContextHolder userContextHolder;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class) || parameter.hasParameterAnnotation(CurrentUserId.class) || parameter.hasParameterAnnotation(ParameterUser.class);

    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws InstantiationException, IllegalAccessException {
        if (parameter.hasParameterAnnotation(ParameterUser.class)) {
            //从请求中构建对象
            Class<? extends SysUserAuth> userEntityClass = sysUserAuthService.getUserEntityClass();
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            SysUserAuth user = userEntityClass.newInstance();
            // 获取自己以及父类中的所有字段，直到Object类为止
            List<Field> allFields = new ArrayList<>();
            Class<?> currentClass = userEntityClass;

            while (currentClass != null && currentClass != Object.class) {
                allFields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
                currentClass = currentClass.getSuperclass();
            }
            for (Field declaredField : allFields) {
                declaredField.setAccessible(true);
                String parameterName = declaredField.getName();
                Class<?> type = declaredField.getType();
                String parameterValue = request.getParameter(parameterName);
                if (parameterValue != null) {
                    declaredField.set(user, StringHelper.convert(parameterValue, type));
                }
            }
            return user;
        } else {
            SysUserAuth user = UserContextHolder.getCurrentUser();
            if (parameter.hasParameterAnnotation(CurrentUserId.class)) {
                return user.getId();
            } else {
                return user;
            }
        }
    }
}
