package io.github.xiechanglei.lan.base.rbac.entity;

import io.github.xiechanglei.lan.base.jpa.baseentity.UUIDIdEntity;
import io.github.xiechanglei.lan.base.rbac.util.ComparedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 资源权限字符字典表，存储各资源所需要的权限字符
 */
@Entity
@Table(name = "sys_resource_code", indexes = {
        @Index(name = "src_resource_id", columnList = "resource_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class SysResourceCode extends UUIDIdEntity implements ComparedEntity {

    /**
     * 资源类型
     */
    public enum ResourceType {MENU, ACTION}

    /**
     * 资源id,菜单id，或者是功能id
     */
    @Column(name = "resource_id", length = 100)
    private String resourceId;

    /**
     * 资源类型
     */
    @Column(name = "resource_type", length = 10)
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    /**
     * 权限字符
     */
    @Column(name = "auth_code", length = 100)
    private String authCode;


    public String buildUniqueId() {
        return resourceId + "_" + authCode;
    }
}
