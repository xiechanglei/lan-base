package io.github.xiechanglei.lan.base.rbac.internal.controller;

import io.github.xiechanglei.lan.base.beans.message.DataFit;
import io.github.xiechanglei.lan.base.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenu;
import io.github.xiechanglei.lan.base.rbac.entity.SysMenuFc;
import io.github.xiechanglei.lan.base.rbac.internal.permission.InternalMenuAuthCodeManager;
import io.github.xiechanglei.lan.base.rbac.service.SysMenuFcService;
import io.github.xiechanglei.lan.base.rbac.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@Validated
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanBaseMenuController {
    private final SysMenuService sysMenuService;
    private final SysMenuFcService sysMenuFcService;

    /**
     * 获取全权限树形结构
     */
    @NeedAuth(InternalMenuAuthCodeManager.QUERY)
    @RequestMapping("/rbac/menu/list")
    public DataFit getMenuAll() {
        return DataFit.of("menus", sysMenuService.getMenuAll())  //全部菜单
                .fit("fcs", sysMenuFcService.getMenuFcAll()); //全部功能
    }


    /**
     * 编辑菜单
     *
     * @param menuCode  菜单的编码
     * @param menuTitle 菜单名称
     * @param menuIcon  菜单图标
     */
    @NeedAuth(InternalMenuAuthCodeManager.UPDATE)
    @RequestMapping("/rbac/menu/update")
    public void updateMenu(@NotBlank(message = "菜单标题不能为空") String menuTitle, String menuCode, String menuIcon, @RequestParam(required = false, defaultValue = "0") Float menuOlder) {
        sysMenuService.updateMenu(menuCode, menuTitle, menuIcon, menuOlder);

    }

    /**
     * 禁用菜单
     */
    @NeedAuth(InternalMenuAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/menu/disableMenu")
    public void disableMenu(String menuCode) {
        sysMenuService.changeMenuStatus(menuCode, SysMenu.MenuStatus.DISABLE);
    }

    /**
     * 启用菜单
     */
    @NeedAuth(InternalMenuAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/menu/enableMenu")
    public void enableMenu(String menuCode) {
        sysMenuService.changeMenuStatus(menuCode, SysMenu.MenuStatus.ENABLE);
    }

    /**
     * 禁用功能
     */
    @NeedAuth(InternalMenuAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/menu/disableFunc")
    public void disableFunc(String funcCode) {
        sysMenuFcService.changeFuncStatus(funcCode, SysMenuFc.FuncStatus.DISABLE);
    }

    /**
     * 启用功能
     */
    @NeedAuth(InternalMenuAuthCodeManager.ENABLE)
    @RequestMapping("/rbac/menu/enableFunc")
    public void enableFunc(String funcCode) {
        sysMenuFcService.changeFuncStatus(funcCode, SysMenuFc.FuncStatus.ENABLE);
    }

    /**
     * 获取菜单的信息
     */
    @NeedAuth(InternalMenuAuthCodeManager.QUERY)
    @RequestMapping("/rbac/menu/get")
    public SysMenu getMenuInfo(String menuCode) {
        return sysMenuService.getMenuInfo(menuCode);
    }
}
