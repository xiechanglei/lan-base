package io.github.xiechanglei.lan.rbac.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 菜单功能的标记注解，用于生成权限相关数据使用，配合AuthMenu 使用:
 * @see io.github.xiechanglei.lan.rbac.internal.permission.InternalUserAuthCodeManager
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(FunctionGroup.class)
@Component
public @interface Function {
    String code();//功能编码，全局唯一

    String title();//功能名称

    String[] authCode();//该功能需要满足的权限编码列表
}
