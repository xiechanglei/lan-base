package io.github.xiechanglei.lan.rbac.annotation;

import io.github.xiechanglei.lan.rbac.custorm.DataAuthScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限范围标注，用于接口的数据权限范围控制，比如一个接口，提供了查询公司员工的信息的功能，
 * 常规条件下，需要限制用户的查询范围，比如我们需要先获取到用户的部门，再使用用户的部门id作为约束进行查询
 * 这里提供了一种可以在接口上自动传递范围约束数据的方式
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthScope {
    Class<? extends DataAuthScope> value();
}
