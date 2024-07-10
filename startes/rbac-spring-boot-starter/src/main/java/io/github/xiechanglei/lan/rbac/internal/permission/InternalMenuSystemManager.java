package io.github.xiechanglei.lan.rbac.internal.permission;


import io.github.xiechanglei.lan.rbac.annotation.Function;
import io.github.xiechanglei.lan.rbac.annotation.Menu;
import io.github.xiechanglei.lan.rbac.entity.base.SysMenu;


/**
 * 系统菜单
 */
@Menu(title = "系统管理", menuType = SysMenu.MenuType.FOLDER, code = "rbac:menu:system:manager", icon = "pro://lan.rbac.icon-sys-manager", order = 99)
public class InternalMenuSystemManager {

    @Menu(title = "用户管理", code = "rbac:menu:user:manager", icon = "pro://lan.rbac.icon-sys-manager-user", order = 1)
    @Function(code = "rbac:user:edit", title = "用户编辑", authCode = {InternalUserAuthCodeManager.CREATE, InternalUserAuthCodeManager.UPDATE, InternalUserAuthCodeManager.ENABLE})
    @Function(code = "rbac:user:select", title = "用户查询", authCode = InternalUserAuthCodeManager.QUERY)
    public static class InternalUserModelManager {
    }

    @Menu(title = "角色管理", code = "rbac:menu:role:manager", icon = "pro://lan.rbac.icon-sys-manager-role", order = 2)
    @Function(code = "rbac:role:edit", title = "角色编辑", authCode = {InternalRoleAuthCodeManager.CREATE, InternalRoleAuthCodeManager.DELETE, InternalRoleAuthCodeManager.GRANT, InternalRoleAuthCodeManager.ENABLE, InternalRoleAuthCodeManager.UPDATE})
    @Function(code = "rbac:role:select", title = "角色查询", authCode = InternalRoleAuthCodeManager.QUERY)
    public static class InternalRoleModelManager {
    }

    @Menu(title = "菜单管理", code = "rbac:menu:menu:manager", icon = "pro://lan.rbac.icon_sys_manager_menu", order = 3)
    @Function(code = "rbac:menu:select", title = "菜单查询", authCode = InternalMenuAuthCodeManager.QUERY)
    @Function(code = "rbac:menu:edit", title = "菜单编辑", authCode = {InternalMenuAuthCodeManager.UPDATE, InternalMenuAuthCodeManager.ENABLE})
    public static class InternalMenuModelManager {
    }

    @Menu(title = "日志管理", code = "rbac:menu:log:manager", icon = "pro://lan.rbac.icon-sys-manager-log", order = 4)
    @Function(code = "rbac:log:select", title = "日志查询", authCode = {InternalLogAuthCodeManager.QUERY, InternalUserAuthCodeManager.QUERY})
    public static class InternalLogModelManager {
    }
}


