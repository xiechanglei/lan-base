package io.github.xiechanglei.lan.rbac.entity.base;

import io.github.xiechanglei.lan.jpa.baseentity.UUIDIdEntity;
import io.github.xiechanglei.lan.rbac.util.ComparedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 资源权限字符字典表，存储各资源所需要的权限字符
 */
@Entity
@Table(name = "sys_resource_code", indexes = {
        @Index(name = "src_resource_id", columnList = "resource_id")
})
@Getter
@Setter
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
