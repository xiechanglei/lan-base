package io.github.xiechanglei.lan.rbac.token;

import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.rbac.properties.LanRbacConfigProperties;
import io.github.xiechanglei.lan.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.lang.string.StringOptional;
import io.github.xiechanglei.lan.web.util.CookieHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * token发现机制的默认实现,必须存在一个TokenInterceptor的实现类，否则无法实现token的权限过滤
 */
@Component
@RequiredArgsConstructor
public class LanTokenInterceptor implements HandlerInterceptor, WebMvcConfigurer {
    private final LanRbacConfigProperties lanRbacConfigProperties;

    /**
     * 拦截器
     * 从请求头中获取token，解析token，获取用户信息，将用户信息放入上下文
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        TokenInfo tokenInfo = TokenHandler.decode(getTokenStrFromRequest(request));
        TokenContextHolder.setCurrentTokenInfo(tokenInfo);
        return true;
    }

    /**
     * 从请求中获取token,没有则从cookie中获取,或者从header中获取
     */
    private String getTokenStrFromRequest(HttpServletRequest request) {
        String REQUEST_KEY = lanRbacConfigProperties.getTokenName();//默认为auth-token
        return StringOptional.of(request.getParameter(REQUEST_KEY))
                .orGet(() -> CookieHelper.getCookie(request, REQUEST_KEY))
                .orGet(() -> request.getHeader(REQUEST_KEY)).get();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns("/**");
    }
}
