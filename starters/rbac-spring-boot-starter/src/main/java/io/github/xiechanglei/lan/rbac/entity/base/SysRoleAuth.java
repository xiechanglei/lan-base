package io.github.xiechanglei.lan.rbac.entity.base;

import io.github.xiechanglei.lan.jpa.baseentity.UUIDIdEntity;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * 角色资源关联表
 */
@Entity
@Table(name = "sys_role_auth", indexes = {
        @Index(name = "sra_role_id", columnList = "role_id"),
        @Index(name = "sra_resource_id", columnList = "resource_id")})
@Data
@SuppressWarnings("all")
public class SysRoleAuth extends UUIDIdEntity {


    /**
     * 角色id
     */
    @Column(name = "role_id", length = 32, nullable = false)
    private String roleId;

    /**
     * 资源id
     */
    @Column(name = "resource_id", length = 100, nullable = false)
    private String resourceId;

    /**
     * 创建角色资源关联
     *
     * @param roleId     角色id
     * @param resourceId 资源id
     * @return 角色资源关联
     */
    public static SysRoleAuth createRoleAuth(String roleId, String resourceId) {
        SysRoleAuth sysRoleAuth = new SysRoleAuth();
        sysRoleAuth.setRoleId(roleId);
        sysRoleAuth.setResourceId(resourceId);
        return sysRoleAuth;
    }
}
