package io.github.xiechanglei.lan.base.rbac.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于spring mvc 的requestMapping的请求拦截，需要设置一个或者多个权限字符，当当前登陆的用户具有指定的全部权限的时候，才可以通过
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedAuth {
    String[] value();// 一个或者多个权限操作码
}
