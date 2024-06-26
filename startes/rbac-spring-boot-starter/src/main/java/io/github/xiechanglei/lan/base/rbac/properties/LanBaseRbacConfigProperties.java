package io.github.xiechanglei.lan.base.rbac.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 角色权限配置属性类
 */
@Data
@Component
@ConfigurationProperties(prefix = "lan.base.rbac")
public class LanBaseRbacConfigProperties {
    /**
     * 是否开启角色权限配置,默认为true
     */
    private boolean enable = true;

    /**
     * 是否拦截权限,默认为true
     */
    private boolean filterAuth = true;

    /**
     * 书否开启数据权限拦截,默认为true
     */
    private boolean filterData = true;

    /**
     * 是否自动创建admin用户
     */
    private boolean createAdmin = true;

    /**
     * 管理员角色名称
     */
    private String roleAdminName = "超级管理员";

    /**
     * 从header，param，cookie中存放token的key， 默认为auth-token
     */
    private String tokenName = "auth-token";

    /**
     * 是否使用controller层的注解，一些子模块可能不需要controller层的注解，只是需要权限控制的功能
     */
    private boolean internalApi = true;

    /**
     * 内置系统管理模块的图标
     */
    private String icon_sys_manager = "";

    /**
     * 内置系统管理模块的角色图标
     */
    private String icon_sys_manager_role = "";

    /**
     * 内置系统管理模块的用户图标
     */
    private String icon_sys_manager_user = "";

    /**
     * 内置系统管理模块的菜单图标
     */
    private String icon_sys_manager_menu = "";
    /**
     * 密码设定规则 非空6-20位
     */
    private String passwordRule = "\\S{6,20}";
}
