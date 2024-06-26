package io.github.xiechanglei.lan.base.rbac.token;

import io.github.xiechanglei.lan.base.utils.string.StringOptional;
import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.base.web.util.CookieHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token发现机制的默认实现,必须存在一个TokenInterceptor的实现类，否则无法实现token的权限过滤
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseTokenInterceptor implements HandlerInterceptor, WebMvcConfigurer {
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;

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
        String REQUEST_KEY = lanBaseRbacConfigProperties.getTokenName();//默认为auth-token
        return StringOptional.of(request.getParameter(REQUEST_KEY))
                .orFillProduce(() -> CookieHelper.getCookie(request, REQUEST_KEY))
                .orFillProduce(() -> request.getHeader(REQUEST_KEY)).get();
    }

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this).addPathPatterns("/**");
    }
}
