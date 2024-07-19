package io.github.xiechanglei.lan.rbac.provide;

import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.service.LanSysUserAuthService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class UserContextHolder {
    private static LanSysUserAuthService lanSysUserAuthService;

    public static final String REQUEST_ATTR_USER_KEY = "BASE_AUTH_USER_INFO";// 存放在request中的用户信息的key

    public UserContextHolder(LanSysUserAuthService sysUserAuthService) {
        UserContextHolder.lanSysUserAuthService = sysUserAuthService;
    }

    /**
     * 获取当前登录用户信息,如果request中没有，从数据库中获取
     */
    public static SysUserAuth getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysUserAuth user = (SysUserAuth) request.getAttribute(REQUEST_ATTR_USER_KEY);
        if (user == null) {
            TokenInfo tokenInfo = TokenContextHolder.getCurrentTokenInfo();
            if (tokenInfo == null) {
                throw BusinessError.USER.USER_NOT_LOGIN;
            }
            // Optional在Java 8中引入，目的是解决  NullPointerExceptions的问题
            user = lanSysUserAuthService.findById(tokenInfo.getUserId()).orElseThrow(() -> BusinessError.USER.USER_NOT_LOGIN);
            // 校验用户序列号与token中的序列号是否一致，不一致表示密码更改，登录过期
            if (!Objects.equals(user.getUserSerial(), tokenInfo.getSerialNumber())) {
                throw BusinessError.USER.USER_LOGIN_EXPIRED;
            }
            // 校验用户是否被禁用
            if (SysUserAuth.UserStatus.DISABLE.equals(user.getUserStatus())) {
                throw BusinessError.USER.USER_DISABLED;
            }
            request.setAttribute(REQUEST_ATTR_USER_KEY, user);
        }
        return user;
    }

    /**
     * 设置当前登陆的用户信息
     * @param user 用户信息
     */
    public static void setCurrentUser(SysUserAuth user) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        request.setAttribute(REQUEST_ATTR_USER_KEY, user);
    }
}
