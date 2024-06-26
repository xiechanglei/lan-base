package io.github.xiechanglei.lan.base.rbac.internal.controller;


import io.github.xiechanglei.lan.base.beans.message.DataFit;
import io.github.xiechanglei.lan.base.rbac.annotation.CurrentUser;
import io.github.xiechanglei.lan.base.rbac.annotation.ParameterUser;
import io.github.xiechanglei.lan.base.rbac.annotation.Password;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.base.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysMenuFcService;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysMenuService;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysRoleService;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysUserAuthService;
import io.github.xiechanglei.lan.base.rbac.token.TokenHandler;
import io.github.xiechanglei.lan.base.rbac.token.TokenInfoManager;
import io.github.xiechanglei.lan.base.web.log.ApiLog;
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
@ConditionalOnProperty(prefix = "lan.base.rbac", name = {"internal-api", "enable"}, havingValue = "true", matchIfMissing = true)
public class LanBaseCurrentUserController {
    private final LanBaseSysUserAuthService sysUserAuthService;
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;
    private final LanBaseSysRoleService lanBaseSysRoleService;
    private final LanBaseSysMenuService lanBaseSysMenuService;
    private final LanBaseSysMenuFcService lanBaseSysMenuFcService;

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
        SysUserAuth loginUser = sysUserAuthService.findByUserNameAndUserPassword(userName, userPassword);
        if (loginUser == null) {
            throw BusinessError.USER.USER_LOGIN_FAILED;
        }
        if (SysUserAuth.UserStatus.DISABLE == loginUser.getUserStatus()) {
            throw BusinessError.USER.USER_DISABLED;
        }
        String token = TokenHandler.encode(TokenInfoManager.buildTokenInfo(loginUser));
        if (injectCookie) {
            Cookie cookie = new Cookie(lanBaseRbacConfigProperties.getTokenName(), token);
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
        sysUserAuthService.updatePassword(user, newPass);
        return TokenHandler.encode(TokenInfoManager.buildTokenInfo(user));
    }

    /**
     * 获取当前用户的信息，包含菜单权限，角色列表，功能
     */
    @ApiLog(value = "获取当前用户的信息")
    @RequestMapping("/rbac/user/current/get")
    public DataFit getCurrentUserInfo(@CurrentUser SysUserAuth user) {
        DataFit result = DataFit.of("user", user).fit("roles", lanBaseSysRoleService.findByUserId(user.getId()));
        if (lanBaseSysRoleService.hasAdminRole(user.getId())) {
            return result.fit("menus", lanBaseSysMenuService.getMenuAll()).fit("fcs", lanBaseSysMenuFcService.getMenuFcAll());
        }
        return result.fit("menus", lanBaseSysMenuService.findByUserId(user.getId())).fit("fcs", lanBaseSysMenuFcService.findByUserId(user.getId()));
    }

    /**
     * 更新当前用户信息
     */
    @ApiLog(value = "更新当前用户信息")
    @RequestMapping("/rbac/user/current/update")
    public void updateCurrentUserInfo(@CurrentUser SysUserAuth user, @ParameterUser SysUserAuth newUser) {
        BeanUtils.copyProperties(newUser, user, "id", "userName", "userPassword", "userStatus", "userSerial", "createTime", "updateTime");
        sysUserAuthService.update(user);
    }

}
