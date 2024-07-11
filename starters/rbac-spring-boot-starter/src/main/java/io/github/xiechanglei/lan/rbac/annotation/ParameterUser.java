package io.github.xiechanglei.lan.rbac.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * springmvc 参数注解，
 * 只是用于接受前端请求中的用户的信息，是因为业务系统中用户的表结构是未知的，resolver可以知道，通过反射的方式进行注入
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterUser {
}
