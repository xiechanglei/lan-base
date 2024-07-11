package io.github.xiechanglei.lan.rbac.internal.permission;


import io.github.xiechanglei.lan.rbac.annotation.Function;
import io.github.xiechanglei.lan.rbac.annotation.Menu;
import io.github.xiechanglei.lan.rbac.entity.base.SysMenu;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;


/**
 * 系统菜单
 */
@Menu(title = "系统管理", menuType = SysMenu.MenuType.FOLDER, code = "rbac:menu:system:manager", icon = "pro://lan.rbac.icon-sys-manager", order = 99)
public class InternalMenuConfig {

    @Menu(title = "用户管理", code = "rbac:menu:user:manager", icon = "pro://lan.rbac.icon-sys-manager-user", order = 1)
    @Function(code = "rbac:user:edit", title = "用户编辑", authCode = {InternalUserAuthCode.CREATE, InternalUserAuthCode.UPDATE, InternalUserAuthCode.ENABLE})
    @Function(code = "rbac:user:select", title = "用户查询", authCode = InternalUserAuthCode.QUERY)
    public static class InternalUserModelManager {
    }

    @Menu(title = "角色管理", code = "rbac:menu:role:manager", icon = "pro://lan.rbac.icon-sys-manager-role", order = 2)
    @Function(code = "rbac:role:edit", title = "角色编辑", authCode = {InternalRoleAuthCode.CREATE, InternalRoleAuthCode.DELETE, InternalRoleAuthCode.GRANT, InternalRoleAuthCode.ENABLE, InternalRoleAuthCode.UPDATE})
    @Function(code = "rbac:role:select", title = "角色查询", authCode = InternalRoleAuthCode.QUERY)
    public static class InternalRoleModelManager {
    }

    @Menu(title = "菜单管理", code = "rbac:menu:menu:manager", icon = "pro://lan.rbac.icon_sys_manager_menu", order = 3)
    @Function(code = "rbac:menu:select", title = "菜单查询", authCode = InternalMenuAuthCode.QUERY)
    @Function(code = "rbac:menu:edit", title = "菜单编辑", authCode = {InternalMenuAuthCode.UPDATE, InternalMenuAuthCode.ENABLE})
    public static class InternalMenuModelManager {
    }

    @ConditionalOnProperty(prefix = "lan.rbac", name = "enable-log", havingValue = "true", matchIfMissing = true)
    @Function(code = "rbac:log:select", title = "日志查询", authCode = {InternalLogAuthCode.QUERY, InternalUserAuthCode.QUERY})
    public static class InternalLogModelManager {
    }
}


