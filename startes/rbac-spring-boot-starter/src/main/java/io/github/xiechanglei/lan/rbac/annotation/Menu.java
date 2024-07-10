package io.github.xiechanglei.lan.rbac.annotation;

import io.github.xiechanglei.lan.rbac.entity.base.SysMenu;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单的标记注解，用于程序自动化生成菜单数据使用的，
 *
 * @see io.github.xiechanglei.lan.rbac.internal.permission.InternalUserAuthCodeManager
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Menu {
    String title(); // 菜单标题

    String code();// 菜单编码,不同的菜单之间应该是唯一的，可以用于前端映射路由器，生成之后不允许改变

    SysMenu.MenuType menuType() default SysMenu.MenuType.PAGE;// 菜单类型

    /**
     * 菜单的图片，通常为icon的classname，如”el-icon-s-operation“ ,
     * 也支持配置文件设置，格式为:"pro://xxxx",xxxx 表示配置参数的名称，
     * 如 pro://io.github.xiechanglei.lan.base.rbac.icon-sys-manager-role
     */
    String icon() default "";

    float order(); // 排序

    /**
     * 只对最外层的AuthMenu修饰的类有效，表示父级菜单的code，空字符串表示是根节点
     * 内部类的parentCode为父类的code值，所以配置的内部类的parentCode参数无效
     *
     */
    String parentCode() default "";
}
