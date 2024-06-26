package io.github.xiechanglei.lan.base.rbac.internal.controller;

import io.github.xiechanglei.lan.base.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.base.rbac.entity.SysRole;
import io.github.xiechanglei.lan.base.rbac.entity.SysUser;
import io.github.xiechanglei.lan.base.rbac.internal.permission.InternalMenuAuthCodeManager;
import io.github.xiechanglei.lan.base.rbac.internal.permission.InternalRoleAuthCodeManager;
import io.github.xiechanglei.lan.base.rbac.internal.permission.InternalUserAuthCodeManager;
import io.github.xiechanglei.lan.base.rbac.service.SysRoleService;
import io.github.xiechanglei.lan.base.rbac.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * 角色相关的接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanBaseRoleController {
    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;

    /**
     * 查询所有角色
     */
    @NeedAuth(InternalRoleAuthCodeManager.QUERY)
    @RequestMapping("/rbac/role/query")
    public Page<SysRole> searchRole(PageRequest pageRequest, @RequestParam(required = false, defaultValue = "") String roleName) {
        return sysRoleService.searchRole(pageRequest, roleName);
    }

    /**
     * 创建角色
     */
    @NeedAuth(InternalRoleAuthCodeManager.CREATE)
    @RequestMapping("/rbac/role/add")
    public void createRole(@Size(min = 1, max = 20, message = "角色名称长度必须在1-20个字符") String roleName) {
        sysRoleService.createRole(roleName);
    }

    /**
     * 根据角色id查询角色详情
     *
     * @param roleId 角色id
     */
    @NeedAuth(InternalRoleAuthCodeManager.QUERY)
    @RequestMapping("/rbac/role/get")
    public SysRole searchRoleById(String roleId) {
        return sysRoleService.getRoleById(roleId);
    }

    /**
     * 更新角色
     */
    @NeedAuth(InternalRoleAuthCodeManager.UPDATE)
    @RequestMapping("/rbac/role/update")
    public void editRole(String roleName, String roleId, String roleRemark) {
        sysRoleService.editRole(roleName, roleId, roleRemark);
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色id
     */
    @NeedAuth(InternalRoleAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/role/disable")
    public void disableRole(String roleId) {
        sysRoleService.changeRoleStatus(roleId, SysRole.RoleStatus.DISABLE);
    }

    /**
     * 启用角色
     *
     * @param roleId 角色id
     */
    @NeedAuth(InternalRoleAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/role/enable")
    public void enableRole(String roleId) {
        sysRoleService.changeRoleStatus(roleId, SysRole.RoleStatus.ENABLE);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    @NeedAuth(InternalRoleAuthCodeManager.DELETE)
    @RequestMapping("/rbac/role/remove")
    public void deleteRole(String roleId) {
        sysRoleService.deleteRole(roleId);
    }

    /**
     * 查询角色权限
     *
     * @param roleId 角色id
     */
    @NeedAuth(InternalRoleAuthCodeManager.QUERY)
    @RequestMapping("/rbac/role/loadResource")
    public List<String> loadRoleResource(String roleId) {
        return sysRoleService.loadRoleResource(roleId);
    }

    /**
     * 设置角色权限
     */
    @NeedAuth({InternalRoleAuthCodeManager.GRANT, InternalMenuAuthCodeManager.QUERY})
    @RequestMapping("/rbac/role/grantResource")
    public void grantResource(String roleId, String[] resourceIds) {
        sysRoleService.grantResource(roleId, resourceIds);
    }

    /**
     * 根据角色ID查询关联的所有用户
     */
    @NeedAuth({InternalRoleAuthCodeManager.QUERY, InternalUserAuthCodeManager.QUERY})
    @RequestMapping("/rbac/role/getUserByRoleId")
    public Page<SysUser> getUserByRoleId(PageRequest pageRequest, String roleId) {
        return sysUserRoleService.getUserByRoleId(pageRequest, roleId);
    }
}
