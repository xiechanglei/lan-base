package io.github.xiechanglei.lan.base.rbac.service;


import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.base.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.base.rbac.provide.UserContextHolder;
import io.github.xiechanglei.lan.base.rbac.repo.SysResourceCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public final class DefaultPermissionService {
    private final SysResourceCodeRepository sysResourceCodeRepository;
    private final SysRoleService sysRoleService;
    private final UserContextHolder userContextHolder;


    /**
     * 检查用户是否可以访问相关的接口
     */
    public void checkPermission(TokenInfo tokenInfo, String[] permissions) {
        if (sysRoleService.hasAdminRole(tokenInfo.getUserId())) {
            return;
        }
        hasPermission(userContextHolder.getCurrentUser(), permissions);
    }

    /**
     * 检查用户是否有指定的权限
     */
    public void hasPermission(SysUserAuth user, String[] permissions) {
        // 判断当前用户是否为超级管理员
        if (sysResourceCodeRepository.findUserAuthCodeIn(user.getId(), permissions).size() != permissions.length) {
            throw BusinessError.USER.USER_NO_PERMISSION;
        }
    }
}
