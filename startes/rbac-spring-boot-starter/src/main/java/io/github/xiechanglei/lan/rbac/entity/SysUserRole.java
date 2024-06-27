package io.github.xiechanglei.lan.rbac.entity;

import io.github.xiechanglei.lan.jpa.baseentity.UUIDIdEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 用户角色关联
 */
@Entity
@Table(name = "sys_user_role", indexes = {
        @Index(name = "sur_user_role_id_unique", unique = true, columnList = "user_id,role_id"),
        @Index(name = "sur_user_id", columnList = "user_id"),
        @Index(name = "sur_role_id", columnList = "role_id")
})
@Setter
@Getter
@SuppressWarnings("all")
public class SysUserRole extends UUIDIdEntity {

    /**
     * 用户id
     */
    @Column(name = "user_id", length = 32, nullable = false)
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id", length = 32, nullable = false)
    private String roleId;

    public static SysUserRole createAuthUserRole(String userId, String roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return sysUserRole;
    }
}
