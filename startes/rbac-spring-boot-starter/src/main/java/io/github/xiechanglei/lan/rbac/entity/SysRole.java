package io.github.xiechanglei.lan.rbac.entity;

import io.github.xiechanglei.lan.jpa.baseentity.UUIDIdAndTimeFieldEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 角色表
 */
@Entity
@Table(name = "sys_role", indexes = {
        @Index(name = "sr_role_name", columnList = "role_name")
})
@Setter
@Getter
@SuppressWarnings("all")
public class SysRole extends UUIDIdAndTimeFieldEntity {

    /**
     * 菜单状态
     */
    public enum RoleStatus {DISABLE, ENABLE}

    /**
     * 角色名称
     */
    @Column(name = "role_name", length = 100, nullable = false)
    private String roleName;

    /**
     * 角色状态
     */
    @Column(name = "role_status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleStatus roleStatus;

    /**
     * 是否是超级管理员
     */
    @Column(name = "is_admin", length = 1, nullable = false)
    private boolean isAdmin;

    /**
     * 角色备注
     */
    @Column(name = "role_remark", length = 255)
    private String roleRemark;

    public static SysRole createAdmin(String roleName) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setRoleStatus(RoleStatus.ENABLE);
        sysRole.setAdmin(true);
        return sysRole;
    }

    public static SysRole creatRole(String roleName) {
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setRoleStatus(RoleStatus.ENABLE);
        sysRole.setAdmin(false);
        return sysRole;
    }
}
