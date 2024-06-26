package io.github.xiechanglei.lan.base.rbac.internal.controller;

import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysRoleService;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色相关的接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanBaseLogController {
    private final LanBaseSysRoleService sysRoleService;
    private final LanBaseSysUserRoleService lanBaseSysUserRoleService;

//    /**
//     * 根据角色ID查询关联的所有用户
//     */
//    @ApiLog(value = "根据角色ID查询关联的所有用户", params = {"roleId"})
//    @NeedAuth({InternalRoleAuthCodeManager.QUERY, InternalUserAuthCodeManager.QUERY})
//    @RequestMapping("/rbac/role/getUserByRoleId")
//    public Page<SysUser> getUserByRoleId(PageRequest pageRequest, String roleId) {
//        return lanBaseSysUserRoleService.getUserByRoleId(pageRequest, roleId);
//    }
}
