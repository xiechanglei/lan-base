package io.github.xiechanglei.lan.rbac.annotation;


import io.github.xiechanglei.lan.rbac.internal.permission.InternalMenuAuthCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限字符串的标记注解，主要用于生成数据的权限配置数据，配合 AuthMenu注解使用，
 * @see InternalMenuAuthCode
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCode {
    String value(); // 权限字符的描述信息
}
