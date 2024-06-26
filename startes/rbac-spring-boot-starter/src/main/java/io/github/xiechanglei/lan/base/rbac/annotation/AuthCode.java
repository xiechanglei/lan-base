package io.github.xiechanglei.lan.base.rbac.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限字符串的标记注解，主要用于生成数据的权限配置数据，配合 AuthMenu注解使用，
 * @see io.github.xiechanglei.lan.base.rbac.internal.permission.InternalMenuAuthCodeManager
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCode {
    String value(); // 权限字符的描述信息
}
