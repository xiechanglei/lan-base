package io.github.xiechanglei.lan.base.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * spring mvc 参数注解，用于获取当前登陆的用户，会查询数据库
 * 解析规则
 * @see io.github.xiechanglei.lan.base.rbac.resolver.LanBaseCurrentUserTypeResolver
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface CurrentUser { }
