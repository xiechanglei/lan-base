package io.github.xiechanglei.lan.rbac.provide;

import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class TokenContextHolder {
    public static final String REQUEST_ATTR_TOKEN_KEY = "BASE_AUTH_TOKEN_INFO";// 存放在request中的token信息的key
    /**
     * 获取当前登录用户的token
     */
    public static TokenInfo getCurrentTokenInfo() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return (TokenInfo) request.getAttribute(REQUEST_ATTR_TOKEN_KEY);
    }

    /**
     * 设置当前登录用户的token
     * @param tokenInfo token信息
     */
    public static void setCurrentTokenInfo(TokenInfo tokenInfo) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        request.setAttribute(REQUEST_ATTR_TOKEN_KEY, tokenInfo);
    }
}
