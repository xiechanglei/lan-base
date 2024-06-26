package io.github.xiechanglei.lan.base.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 获取主键对应的用户信息，比如用户传入id=3 ,自动注入id=3的用户信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface User {
    String value() default "id"; // 接口接受的用来查询用户的id
}
