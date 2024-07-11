package io.github.xiechanglei.lan.rbac.entity.base;

import io.github.xiechanglei.lan.rbac.util.ComparedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * 功能表，用以存储菜单下的功能模块，比如，在用户管理这个菜单下面，有用户查询、用户创建、用户删除等功能模块
 * 前端可以根据这些功能模块来控制按钮的显示与隐藏，数据是否加载等逻辑
 */
@Data
@Entity
@Table(name = "sys_menu_fc", indexes = {
        @Index(name = "smf_menu_id", columnList = "menu_id")
})
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class SysMenuFc implements ComparedEntity {


    @Override
    public Object buildUniqueId() {
        return getFcCode();
    }


    /**
     * 功能状态
     */
    public enum FuncStatus {DISABLE, ENABLE}

    /**
     * 功能编码,全局唯一，不能为空，设计成主键
     */
    @Id
    @Column(name = "fc_code", length = 100)
    private String fcCode;

    /**
     * 功能名称，用于描述功能的作用，如查询用户 ，不可修改
     */
    @Column(name = "fc_title", length = 100)
    private String fcTitle;


    /**
     * 菜单id，不可修改
     */
    @Column(name = "menu_id", length = 100)
    private String menuId;

    /**
     * 功能状态，可以修改，
     */
    @Column(name = "fc_status", length = 10)
    @Enumerated(EnumType.STRING)
    private FuncStatus fcStatus;


    public SysMenuFc(String title, String fcCode, String menuCode) {
        this.setFcTitle(title);
        this.setFcCode(fcCode);
        this.setMenuId(menuCode);
        this.setFcStatus(FuncStatus.ENABLE);
    }

    @Override
    public boolean changeIfNotSame(ComparedEntity comparedEntity) {
        SysMenuFc menuFc = (SysMenuFc) comparedEntity;
        boolean isChange = false;

        if (!Objects.equals(this.menuId, menuFc.getMenuId()) && (isChange = true)) {
            this.menuId = menuFc.getMenuId();
        }
        if (!Objects.equals(this.fcTitle, menuFc.getFcTitle()) && (isChange = true)) {
            this.fcTitle = menuFc.getFcTitle();
        }
        return isChange;
    }

}
