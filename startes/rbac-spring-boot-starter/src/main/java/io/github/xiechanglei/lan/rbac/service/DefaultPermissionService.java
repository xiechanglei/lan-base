package io.github.xiechanglei.lan.rbac.service;


import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.provide.UserContextHolder;
import io.github.xiechanglei.lan.rbac.repo.LanSysResourceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class DefaultPermissionService {
    private final LanSysResourceCodeRepository lanSysResourceCodeRepository;
    private final LanSysRoleService sysRoleService;
    private final UserContextHolder userContextHolder;


    /**
     * 检查用户是否可以访问相关的接口
     */
    public void checkPermission(TokenInfo tokenInfo, String[] permissions) {
        if (sysRoleService.hasAdminRole(tokenInfo.getUserId())) {
            return;
        }
        hasPermission(UserContextHolder.getCurrentUser(), permissions);
    }

    /**
     * 检查用户是否有指定的权限
     */
    public void hasPermission(SysUserAuth user, String[] permissions) {
        // 判断当前用户是否为超级管理员
        if (lanSysResourceCodeRepository.findUserAuthCodeIn(user.getId(), permissions).size() != permissions.length) {
            throw BusinessError.USER.USER_NO_PERMISSION;
        }
    }
}
