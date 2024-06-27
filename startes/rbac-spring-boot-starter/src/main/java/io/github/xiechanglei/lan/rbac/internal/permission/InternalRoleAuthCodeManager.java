package io.github.xiechanglei.lan.rbac.internal.permission;


import io.github.xiechanglei.lan.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.rbac.annotation.AuthModule;

/**
 *
 * 角色模块
 */
@AuthModule("角色模块")
public class InternalRoleAuthCodeManager {
    // title
    @AuthCode("角色创建")
    public static final String CREATE = "rbac:role:create";

    @AuthCode("角色编辑")
    public static final String UPDATE = "rbac:role:update";

    @AuthCode("角色删除")
    public static final String DELETE = "rbac:role:delete";

    @AuthCode("角色查询")
    public static final String QUERY= "rbac:role:query";

    @AuthCode("角色授权")
    public static final String GRANT = "rbac:role:grant";

    @AuthCode("角色禁用启用")
    public static final String ENABLE = "rbac:role:enable";
}
