package io.github.xiechanglei.lan.base.rbac.dsl;

import io.github.xiechanglei.lan.base.rbac.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.base.rbac.entity.QSysMenu.sysMenu;
import static io.github.xiechanglei.lan.base.rbac.entity.QSysRole.sysRole;
import static io.github.xiechanglei.lan.base.rbac.entity.QSysRoleAuth.sysRoleAuth;
import static io.github.xiechanglei.lan.base.rbac.entity.QSysUserRole.sysUserRole;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class SysMenuDsl {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据用户id查询菜单
     *
     * @param userId 用户id
     */
    public List<SysMenu> findByUserId(String userId) {
        return jpaQueryFactory.select(sysMenu).from(sysUserRole, sysRole, sysRoleAuth, sysMenu)
                .where(sysUserRole.roleId.eq(sysRole.id)
                        .and(sysRole.id.eq(sysRoleAuth.roleId))
                        .and(sysRoleAuth.resourceId.eq(sysMenu.menuCode))
                        .and(sysUserRole.userId.eq(userId))
                        .and(sysRole.roleStatus.eq(SysRole.RoleStatus.ENABLE))
                        .and(sysMenu.menuStatus.eq(SysMenu.MenuStatus.ENABLE)))
                .distinct().fetch();
    }

}
