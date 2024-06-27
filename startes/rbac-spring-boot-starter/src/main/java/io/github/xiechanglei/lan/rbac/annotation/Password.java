package io.github.xiechanglei.lan.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对前端的用户的密码明文进行加密，与数据库中的数据进行对比
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Password {
    boolean check() default true; // 是否经过密码校验规则，可配置正则表达式，默认为true
}
