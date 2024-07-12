package io.github.xiechanglei.lan.rbac.internal.controller;


import io.github.xiechanglei.lan.beans.message.DataFit;
import io.github.xiechanglei.lan.lang.string.StringOptional;
import io.github.xiechanglei.lan.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.rbac.annotation.ParameterUser;
import io.github.xiechanglei.lan.rbac.annotation.Password;
import io.github.xiechanglei.lan.rbac.annotation.User;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalUserAuthCode;
import io.github.xiechanglei.lan.rbac.service.LanSysRoleService;
import io.github.xiechanglei.lan.rbac.service.LanSysUserAuthService;
import io.github.xiechanglei.lan.rbac.service.LanSysUserRoleService;
import io.github.xiechanglei.lan.web.log.ApiLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanUserController {
    private final LanSysUserAuthService lanSysUserAuthService;
    private final LanSysUserRoleService lanSysUserRoleService;
    private final LanSysRoleService lanSysRoleService;

    /**
     * 修改用户的密码
     */
    @ApiLog(value = "修改用户密码", params = {"id"})
    @RequestMapping("/rbac/user/changePass")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void changePass(@Password String newPass, @User SysUserAuth user) {
        lanSysUserAuthService.updatePassword(user, newPass);
    }


    /**
     * 获取用户信息,只包含角色信息
     */
    @ApiLog(value = "获取用户信息", params = {"id"})
    @RequestMapping("/rbac/user/get")
    @NeedAuth(InternalUserAuthCode.QUERY)
    public DataFit getUserInfo(@User SysUserAuth user) {
        return DataFit.of("user", user).fit("roles", lanSysRoleService.findByUserId(user.getId()));

    }

    /**
     * 更新用户信息
     *
     * @param user 指定用户
     */
    @ApiLog(value = "更新用户信息", params = {"id"})
    @RequestMapping("/rbac/user/update")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void updateUser(@User SysUserAuth user, @ParameterUser SysUserAuth newUser) {
        BeanUtils.copyProperties(newUser, user, "id", "userName", "userPassword", "userStatus", "userSerial", "createTime", "updateTime");
        lanSysUserAuthService.update(user);
    }


    /**
     * 禁用用户
     */
    @ApiLog(value = "禁用用户", params = {"id"})
    @RequestMapping("/rbac/user/disable")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void disable(String id) {
        // 管理员用户不能禁用
        if (lanSysRoleService.hasAdminRole(id)) {
            throw BusinessError.USER.USER_CAN_NOT_OPERATE;
        }
        lanSysUserAuthService.disableUser(id);
    }

    /**
     * 启用用户
     */
    @ApiLog(value = "启用用户", params = {"id"})
    @RequestMapping("/rbac/user/enable")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void enable(String id) {
        lanSysUserAuthService.enableUser(id);
    }

    /**
     * 查询用户
     *
     * @param word        查询关键字
     * @param pageRequest 分页信息
     */
    @ApiLog(value = "查询用户", params = {"word"})
    @RequestMapping("/rbac/user/query")
    @NeedAuth({InternalUserAuthCode.QUERY})
    public Page<SysUserAuth> searchUser(String word, PageRequest pageRequest) {
        return lanSysUserAuthService.searchUser(word, pageRequest);
    }


    /**
     * 添加用户
     */
    @ApiLog(value = "添加用户", params = {"userName"})
    @RequestMapping("/rbac/user/add")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void addUser(@ParameterUser SysUserAuth user, @Password String password) {
        StringOptional.of(user.getUserName()).ifNotPresentThrow(BusinessError.USER.USER_NAME_IS_EMPTY);
        if (lanSysUserAuthService.existsByUsername(user.getUserName())) {
            throw BusinessError.USER.USER_EXISTS;
        }
        user.setUserPassword(password);
        user.setId(null);
        user.setUserSerial(SysUserAuth.DEFAULT_USER_SERIAL);
        user.setUserStatus(SysUserAuth.UserStatus.ENABLE);
        lanSysUserAuthService.add(user);
    }

    /**
     * 给用户授予角色,一个用户可以授予多个角色
     *
     * @param user    用户
     * @param roleIds 角色id数组
     */
    @ApiLog(value = "给用户授予角色", params = {"id", "roleIds"})
    @RequestMapping("/rbac/user/grantRole")
    @NeedAuth(InternalUserAuthCode.UPDATE)
    public void grantRoleToUser(@User SysUserAuth user, String[] roleIds) {
        lanSysUserRoleService.grantRoles(user.getId(), roleIds);
    }
}
