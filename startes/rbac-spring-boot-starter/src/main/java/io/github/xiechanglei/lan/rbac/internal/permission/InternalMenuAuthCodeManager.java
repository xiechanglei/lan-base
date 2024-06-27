package io.github.xiechanglei.lan.rbac.internal.permission;


import io.github.xiechanglei.lan.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.rbac.annotation.AuthModule;

/**
 * 菜单模块
 */
@AuthModule("菜单模块")
public class InternalMenuAuthCodeManager {
    @AuthCode("菜单查询")
    public static final String QUERY= "rbac:menu:query";

    @AuthCode("菜单编辑")
    public static final String UPDATE = "rbac:menu:update";

    @AuthCode("菜单禁用启用")
    public static final String ENABLE = "rbac:menu:enable";

}
