package io.github.xiechanglei.lan.rbac.annotation;


import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限字符串所属模块的标记注解，主要用于生成数据的权限配置数据，配合 AuthCode 注解使用，
 * @see io.github.xiechanglei.lan.rbac.internal.permission.InternalMenuAuthCodeManager
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface AuthModule {
    String value(); //模块名称备注
}
