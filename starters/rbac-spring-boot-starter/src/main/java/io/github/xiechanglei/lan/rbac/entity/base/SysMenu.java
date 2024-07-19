package io.github.xiechanglei.lan.rbac.entity.base;

import io.github.xiechanglei.lan.lang.string.StringOptional;
import io.github.xiechanglei.lan.rbac.util.ComparedEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * 菜单表，这实际上是前端的一个业务功能模块（菜单管理），用于生成前端的菜单树，对于后端的权限过滤体系来说是两个东西
 * 菜单表的数据不可以增加或者删除，不可以修改菜单的结构
 */

@Data
@Entity
@Table(name = "sys_menu")
@NoArgsConstructor
@SuppressWarnings("all")
public class SysMenu implements ComparedEntity {

    /**
     * 菜单状态
     */
    public enum MenuStatus {DISABLE, ENABLE}

    /**
     * 菜单类型
     */
    public enum MenuType {FOLDER, PAGE}

    /**
     * 菜单编码，全局唯一，不能为空，设计成主键
     */
    @Id
    @Column(name = "menu_code", length = 100)
    private String menuCode;

    /**
     * 菜单名称，可以修改
     */
    @Column(name = "menu_title", length = 100)
    private String menuTitle;


    /**
     * 菜单类型，不可修改
     */
    @Column(name = "menu_type", length = 10)
    @Enumerated(EnumType.STRING)
    private MenuType menuType;


    /**
     * 父级菜单id，根节点的父级菜单id为 null,不可修改
     */
    @Column(name = "parent_id", length = 100)
    private String parentId;


    /**
     * 菜单排序，支持小数，可以修改
     */
    @Column(name = "menu_order", length = 10)
    private Float menuOrder;


    /**
     * 菜单图标，一般存储图标的icon-class类名称，实际项目实际配置，可以修改
     */
    @Column(name = "menu_icon", length = 100)
    private String menuIcon;


    /**
     * 菜单状态，禁用表示不可见，可以修改
     */
    @Column(name = "menu_status", length = 10)
    @Enumerated(EnumType.STRING)
    private MenuStatus menuStatus;


    @Override
    public Object buildUniqueId() {
        return getMenuCode();
    }

    @Override
    public boolean changeIfNotSame(ComparedEntity comparedEntity) {
        SysMenu menu = (SysMenu) comparedEntity;
        boolean isChange = false;
        if (!Objects.equals(this.menuType, menu.getMenuType()) && (isChange = true)) {
            this.menuType = menu.getMenuType();
        }
        if (!Objects.equals(this.parentId, menu.getParentId()) && (isChange = true)) {
            this.parentId = menu.getParentId();
        }
        return isChange;
    }


    public SysMenu(String title, String code, MenuType menuType, String icon, float order, String parentId) {
        this.setMenuTitle(title);
        this.setMenuCode(code);
        this.setMenuType(menuType);
        this.setMenuIcon(icon);
        this.setMenuOrder(order);
        this.setMenuStatus(MenuStatus.ENABLE);
        this.parentId = StringOptional.of(parentId).or(null).get();
    }

}
