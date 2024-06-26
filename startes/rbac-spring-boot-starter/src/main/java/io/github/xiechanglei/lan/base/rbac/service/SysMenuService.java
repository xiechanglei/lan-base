package io.github.xiechanglei.lan.base.rbac.service;

import io.github.xiechanglei.lan.base.rbac.dsl.SysMenuDsl;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenu;
import io.github.xiechanglei.lan.base.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.base.rbac.repo.SysMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysMenuService {
    private final SysMenuRepository sysMenuRepository;
    private final SysMenuDsl sysMenuDsl;

    /**
     * 修改菜单状态
     *
     * @param menuCode   菜单编码
     * @param menuStatus 菜单状态
     */
    public void changeMenuStatus(String menuCode, SysMenu.MenuStatus menuStatus) {
        SysMenu sysMenu = getMenuInfo(menuCode);
        sysMenu.setMenuStatus(menuStatus);
        sysMenuRepository.save(sysMenu);
    }

    /**
     * 修改菜单
     *
     * @param menuCode  菜单编码
     * @param menuTitle 菜单标题
     * @param menuIcon  菜单图标
     * @param menuOlder 菜单排序
     */
    public void updateMenu(String menuCode, String menuTitle, String menuIcon, Float menuOlder) {
        // 判断菜单名称是否存在
        if (sysMenuRepository.existsByMenuTitleAndMenuCodeNot(menuTitle, menuCode)) {
            throw BusinessError.MENU.MENU_TITLE_EXISTS;
        }
        SysMenu menu = getMenuInfo(menuCode);
        menu.setMenuTitle(menuTitle);
        menu.setMenuIcon(menuIcon);
        menu.setMenuOrder(menuOlder);
        sysMenuRepository.save(menu);
    }

    /**
     * 获取菜单的信息
     *
     * @param menuCode 菜单编码
     */
    public SysMenu getMenuInfo(String menuCode) {
        return sysMenuRepository.findById(menuCode).orElseThrow(() -> BusinessError.MENU.MENU_NOT_FOUND);
    }

    /**
     * 查询用户有权限访问的菜单列表
     *
     * @param id 用户id
     */
    public List<SysMenu> findByUserId(String id) {
        return sysMenuDsl.findByUserId(id);
    }

    /**
     * 获取所有菜单并且排序
     */
    public List<SysMenu> getMenuAll() {
        return sysMenuRepository.findAll(Sort.by(Sort.Direction.ASC, "menuOrder"));
    }


}
