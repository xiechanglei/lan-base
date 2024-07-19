package io.github.xiechanglei.lan.rbac.internal.controller;

import io.github.xiechanglei.lan.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.rbac.entity.base.SysRole;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalMenuAuthCode;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalRoleAuthCode;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalUserAuthCode;
import io.github.xiechanglei.lan.rbac.service.LanSysRoleService;
import io.github.xiechanglei.lan.rbac.service.LanSysUserRoleService;
import io.github.xiechanglei.lan.web.log.ApiLog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * 角色相关的接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanRoleController {
    private final LanSysRoleService lanSysRoleService;
    private final LanSysUserRoleService lanSysUserRoleService;

    /**
     * 查询所有角色
     */
    @ApiLog(value = "查询所有角色", params = {"roleName"})
    @NeedAuth(InternalRoleAuthCode.QUERY)
    @RequestMapping("/rbac/role/query")
    public Page<SysRole> searchRole(PageRequest pageRequest, @RequestParam(required = false, defaultValue = "") String roleName) {
        return lanSysRoleService.searchRole(pageRequest, roleName);
    }

    /**
     * 创建角色
     */
    @ApiLog(value = "创建角色", params = {"roleName"})
    @NeedAuth(InternalRoleAuthCode.CREATE)
    @RequestMapping("/rbac/role/add")
    public void createRole(@Size(min = 1, max = 20, message = "角色名称长度必须在1-20个字符") String roleName) {
        lanSysRoleService.createRole(roleName);
    }

    /**
     * 根据角色id查询角色详情
     *
     * @param roleId 角色id
     */
    @ApiLog(value = "查询角色详情", params = {"roleId"})
    @NeedAuth(InternalRoleAuthCode.QUERY)
    @RequestMapping("/rbac/role/get")
    public SysRole searchRoleById(String roleId) {
        return lanSysRoleService.getRoleById(roleId);
    }

    /**
     * 更新角色
     */
    @ApiLog(value = "更新角色", params = {"roleName", "roleId", "roleRemark"})
    @NeedAuth(InternalRoleAuthCode.UPDATE)
    @RequestMapping("/rbac/role/update")
    public void editRole(String roleName, String roleId, String roleRemark) {
        lanSysRoleService.editRole(roleName, roleId, roleRemark);
    }

    /**
     * 禁用角色
     *
     * @param roleId 角色id
     */
    @ApiLog(value = "禁用角色", params = {"roleId"})
    @NeedAuth(InternalRoleAuthCode.ENABLE)
    @RequestMapping("/rbac/role/disable")
    public void disableRole(String roleId) {
        lanSysRoleService.changeRoleStatus(roleId, SysRole.RoleStatus.DISABLE);
    }

    /**
     * 启用角色
     *
     * @param roleId 角色id
     */
    @ApiLog(value = "启用角色", params = {"roleId"})
    @NeedAuth(InternalRoleAuthCode.ENABLE)
    @RequestMapping("/rbac/role/enable")
    public void enableRole(String roleId) {
        lanSysRoleService.changeRoleStatus(roleId, SysRole.RoleStatus.ENABLE);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    @ApiLog(value = "删除角色", params = {"roleId"})
    @NeedAuth(InternalRoleAuthCode.DELETE)
    @RequestMapping("/rbac/role/remove")
    public void deleteRole(String roleId) {
        lanSysRoleService.deleteRole(roleId);
    }

    /**
     * 查询角色权限
     *
     * @param roleId 角色id
     */
    @ApiLog(value = "查询角色权限", params = {"roleId"})
    @NeedAuth(InternalRoleAuthCode.QUERY)
    @RequestMapping("/rbac/role/loadResource")
    public List<String> loadRoleResource(String roleId) {
        return lanSysRoleService.loadRoleResource(roleId);
    }

    /**
     * 设置角色权限
     */
    @ApiLog(value = "设置角色权限", params = {"roleId"})
    @NeedAuth({InternalRoleAuthCode.GRANT, InternalMenuAuthCode.QUERY})
    @RequestMapping("/rbac/role/grantResource")
    public void grantResource(String roleId, String[] resourceIds) {
        lanSysRoleService.grantResource(roleId, resourceIds);
    }

    /**
     * 根据角色ID查询关联的所有用户
     */
    @ApiLog(value = "根据角色ID查询关联的所有用户", params = {"roleId"})
    @NeedAuth({InternalRoleAuthCode.QUERY, InternalUserAuthCode.QUERY})
    @RequestMapping("/rbac/role/getUserByRoleId")
    public Page<SysUserAuth> getUserByRoleId(PageRequest pageRequest, String roleId) {
        return lanSysUserRoleService.getUserByRoleId(roleId, pageRequest);
    }
}
