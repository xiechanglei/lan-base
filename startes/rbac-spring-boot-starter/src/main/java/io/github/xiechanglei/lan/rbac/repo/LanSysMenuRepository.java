package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysMenuRepository  菜单表
 */
public interface LanSysMenuRepository extends JpaRepository<SysMenu, String> {


    /**
     * 查询除了指定菜单编码外，是否存在对应的菜单名称
     *
     * @param menuTitle 菜单名称
     * @param menuCode  菜单编码
     */
    boolean existsByMenuTitleAndMenuCodeNot(String menuTitle, String menuCode);
}
