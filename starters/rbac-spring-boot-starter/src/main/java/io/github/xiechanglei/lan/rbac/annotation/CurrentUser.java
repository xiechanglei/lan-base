package io.github.xiechanglei.lan.rbac.annotation;

import io.github.xiechanglei.lan.rbac.resolver.LanCurrentUserTypeResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * spring mvc 参数注解，用于获取当前登陆的用户，会查询数据库
 * 解析规则
 * @see LanCurrentUserTypeResolver
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CurrentUser { }
