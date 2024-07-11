package io.github.xiechanglei.lan.rbac.internal.permission;

import io.github.xiechanglei.lan.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.rbac.annotation.AuthModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@AuthModule("日志模块")
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable-log", havingValue = "true", matchIfMissing = true)
public class InternalLogAuthCode {

    @AuthCode("日志查询")
    public static final String QUERY= "rbac:log:query";

}
