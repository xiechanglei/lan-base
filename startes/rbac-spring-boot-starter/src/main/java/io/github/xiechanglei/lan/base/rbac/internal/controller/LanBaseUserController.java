package io.github.xiechanglei.lan.base.rbac.internal.controller;


import io.github.xiechanglei.lan.base.beans.message.DataFit;
import io.github.xiechanglei.lan.base.utils.string.StringOptional;
import io.github.xiechanglei.lan.base.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.base.rbac.annotation.ParameterUser;
import io.github.xiechanglei.lan.base.rbac.annotation.Password;
import io.github.xiechanglei.lan.base.rbac.annotation.User;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.base.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.base.rbac.internal.permission.InternalUserAuthCodeManager;
import io.github.xiechanglei.lan.base.rbac.service.SysRoleService;
import io.github.xiechanglei.lan.base.rbac.service.SysUserAuthService;
import io.github.xiechanglei.lan.base.rbac.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanBaseUserController {
    private final SysUserAuthService sysUserAuthService;
    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;

    /**
     * 修改用户的密码
     */
    @RequestMapping("/rbac/user/changePass")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void changePass(@Password String newPass, @User SysUserAuth user) {
        sysUserAuthService.updatePassword(user, newPass);
    }


    /**
     * 获取用户信息,只包含角色信息
     */
    @RequestMapping("/rbac/user/get")
    @NeedAuth(InternalUserAuthCodeManager.QUERY)
    public DataFit getUserInfo(@User SysUserAuth user) {
        return DataFit.of("user", user).fit("roles", sysRoleService.findByUserId(user.getId()));

    }

    /**
     * 更新用户信息
     *
     * @param user 指定用户
     */
    @RequestMapping("/rbac/user/update")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void updateUser(@User SysUserAuth user, @ParameterUser SysUserAuth newUser) {
        BeanUtils.copyProperties(newUser, user, "id", "userName", "userPassword", "userStatus", "userSerial", "createTime", "updateTime");
        sysUserAuthService.update(user);
    }


    /**
     * 禁用用户
     */
    @RequestMapping("/rbac/user/disable")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void disable(String id) {
        // 管理员用户不能禁用
        if (sysRoleService.hasAdminRole(id)) {
            throw BusinessError.USER.USER_CAN_NOT_OPERATE;
        }
        sysUserAuthService.disableUser(id);
    }

    /**
     * 启用用户
     */
    @RequestMapping("/rbac/user/enable")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void enable(String id) {
        sysUserAuthService.enableUser(id);
    }

    /**
     * 查询用户
     *
     * @param word        查询关键字
     * @param pageRequest 分页信息
     */
    @RequestMapping("/rbac/user/query")
    @NeedAuth({InternalUserAuthCodeManager.QUERY})
    public Page<SysUserAuth> searchUser(String word, PageRequest pageRequest) {
        return sysUserAuthService.searchUser(word, pageRequest);
    }


    /**
     * 添加用户
     */
    @RequestMapping("/rbac/user/add")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void addUser(@ParameterUser SysUserAuth user, @Password String password) {
        StringOptional.of(user.getUserName()).ifNotPresentThrow(BusinessError.USER.USER_NAME_IS_EMPTY);
        if (sysUserAuthService.existsByUsername(user.getUserName())) {
            throw BusinessError.USER.USER_EXISTS;
        }
        user.setUserPassword(password);
        user.setId(null);
        user.setUserSerial(SysUserAuth.DEFAULT_USER_SERIAL);
        user.setUserStatus(SysUserAuth.UserStatus.ENABLE);
        sysUserAuthService.add(user);
    }

    /**
     * 给用户授予角色,一个用户可以授予多个角色
     *
     * @param user    用户
     * @param roleIds 角色id数组
     */
    @RequestMapping("/rbac/user/grantRole")
    @NeedAuth(InternalUserAuthCodeManager.UPDATE)
    public void grantRoleToUser(@User SysUserAuth user, String[] roleIds) {
        sysUserRoleService.grantRoles(user.getId(), roleIds);
    }
}
