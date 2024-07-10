package io.github.xiechanglei.lan.rbac.internal.controller;


import io.github.xiechanglei.lan.beans.message.DataFit;
import io.github.xiechanglei.lan.rbac.annotation.CurrentUser;
import io.github.xiechanglei.lan.rbac.annotation.ParameterUser;
import io.github.xiechanglei.lan.rbac.annotation.Password;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.properties.LanRbacConfigProperties;
import io.github.xiechanglei.lan.rbac.service.LanSysMenuFcService;
import io.github.xiechanglei.lan.rbac.service.LanSysMenuService;
import io.github.xiechanglei.lan.rbac.service.LanSysRoleService;
import io.github.xiechanglei.lan.rbac.service.LanSysUserAuthService;
import io.github.xiechanglei.lan.rbac.token.TokenHandler;
import io.github.xiechanglei.lan.rbac.token.TokenInfoManager;
import io.github.xiechanglei.lan.web.log.ApiLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanCurrentUserController {
    private final LanSysUserAuthService lanSysUserAuthService;
    private final LanRbacConfigProperties lanRbacConfigProperties;
    private final LanSysRoleService lanSysRoleService;
    private final LanSysMenuService lanSysMenuService;
    private final LanSysMenuFcService lanSysMenuFcService;

    /**
     * 用户登录
     *
     * @param userName     用户名
     * @param userPassword 密码
     * @param injectCookie 是否注入cookie
     * @return token
     */
    @ApiLog(value = "用户登录", params = {"userName", "userPassword"})
    @RequestMapping("/rbac/user/current/login")
    public String login(String userName, @Password String userPassword, @RequestParam(required = false, defaultValue = "false") Boolean injectCookie) {
        SysUserAuth loginUser = lanSysUserAuthService.findByUserNameAndUserPassword(userName, userPassword);
        if (loginUser == null) {
            throw BusinessError.USER.USER_LOGIN_FAILED;
        }
        if (SysUserAuth.UserStatus.DISABLE == loginUser.getUserStatus()) {
            throw BusinessError.USER.USER_DISABLED;
        }
        String token = TokenHandler.encode(TokenInfoManager.buildTokenInfo(loginUser));
        if (injectCookie) {
            Cookie cookie = new Cookie(lanRbacConfigProperties.getTokenName(), token);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7);
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            Objects.requireNonNull(Objects.requireNonNull(attributes).getResponse()).addCookie(cookie);
        }
        return token;
    }


    /**
     * 修改当前用户的密码，需要传入旧密码进行校验，修改成功之后返回新的token
     *
     * @param newPass 新密码
     * @param oldPass 旧密码
     */
    @ApiLog(value = "修改当前用户的密码")
    @RequestMapping("/rbac/user/current/changePass")
    public String changeMyPass(@Password String newPass, @Password(check = false) String oldPass, @CurrentUser SysUserAuth user) {
        if (!user.getUserPassword().equals(oldPass)) {
            throw BusinessError.USER.USER_PASSWORD_ERROR;
        }
        lanSysUserAuthService.updatePassword(user, newPass);
        return TokenHandler.encode(TokenInfoManager.buildTokenInfo(user));
    }

    /**
     * 获取当前用户的信息，包含菜单权限，角色列表，功能
     */
    @ApiLog(value = "获取当前用户的信息")
    @RequestMapping("/rbac/user/current/get")
    public DataFit getCurrentUserInfo(@CurrentUser SysUserAuth user) {
        DataFit result = DataFit.of("user", user).fit("roles", lanSysRoleService.findByUserId(user.getId()));
        if (lanSysRoleService.hasAdminRole(user.getId())) {
            return result.fit("menus", lanSysMenuService.getMenuAll()).fit("fcs", lanSysMenuFcService.getMenuFcAll());
        }
        return result.fit("menus", lanSysMenuService.findByUserId(user.getId())).fit("fcs", lanSysMenuFcService.findByUserId(user.getId()));
    }

    /**
     * 更新当前用户信息
     */
    @ApiLog(value = "更新当前用户信息")
    @RequestMapping("/rbac/user/current/update")
    public void updateCurrentUserInfo(@CurrentUser SysUserAuth user, @ParameterUser SysUserAuth newUser) {
        BeanUtils.copyProperties(newUser, user, "id", "userName", "userPassword", "userStatus", "userSerial", "createTime", "updateTime");
        lanSysUserAuthService.update(user);
    }

}
