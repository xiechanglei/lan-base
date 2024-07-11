package io.github.xiechanglei.lan.rbac.init.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 在项目启动的时候，自动更新权限数据,更新的规则是:
 * 1.寻找所有的带有@AuthMenu注解和AuthFunction注解的类，将其注解的code和title等保存到数据库中
 * 在项目启动的时候，自动更新权限相关的数据.包括:
 * 1. 权限字符字典表，全表增量更新
 * 2. 菜单表，需要注意的是，菜单表的数据是允许用户修改的，那么只能更新部分字段
 * 3. 功能表，全表增量更新
 * 4. 资源权限关系表，全表增量更新
 * 5. 角色表，检查是否有管理员角色，没有则创建
 * 6. 角色资源关系表 删除不存在的资源所占用的关系数据
 * 7. 用户表 检查是否有管理员用户，没有则创建
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class LanRbacDataInitiation {
    private final LanRbacAuthCodeInitiation lanRbacAuthCodeInitiation;
    private final LanRbacMenuAndFuncInitiation lanRbacMenuAndFuncInitiation;
    private final LanRbacRoleInitiation lanRbacRoleInitiation;

    public void initData(ApplicationContext applicationContext) {
        // 更新权限数据
        log.info("更新权限数据...");
        lanRbacAuthCodeInitiation.initData(applicationContext);
        // 更新菜单和功能数据
        List<String> allResourceIds = lanRbacMenuAndFuncInitiation.initData(applicationContext);
        // 更新角色数据
        lanRbacRoleInitiation.initData(allResourceIds);
    }
}
