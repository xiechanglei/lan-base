package io.github.xiechanglei.lan.base.rbac.internal.permission;


import io.github.xiechanglei.lan.base.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.base.rbac.annotation.AuthModule;

/**
 * 用户模块
 */
@AuthModule("用户模块")
public class InternalUserAuthCodeManager {
    // title
    @AuthCode("用户创建")
    public static final String CREATE = "rbac:user:create";

    @AuthCode("用户查询")
    public static final String QUERY= "rbac:user:query";

    @AuthCode("用户禁用启用")
    public static final String ENABLE = "rbac:user:enable";

    @AuthCode("用户编辑")
    public static final String UPDATE= "rbac:user:update";
}
