package io.github.xiechanglei.lan.base.rbac.internal.permission;

import io.github.xiechanglei.lan.base.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.base.rbac.annotation.AuthModule;

@AuthModule("日志模块")
public class InternalLogAuthCodeManager {

    @AuthCode("日志查询")
    public static final String QUERY= "rbac:log:query";

}
