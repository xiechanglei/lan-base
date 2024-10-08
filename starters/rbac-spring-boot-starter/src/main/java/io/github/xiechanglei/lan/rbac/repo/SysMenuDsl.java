package io.github.xiechanglei.lan.rbac.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.rbac.entity.base.SysMenu;
import io.github.xiechanglei.lan.rbac.entity.base.SysRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.rbac.entity.base.QSysMenu.sysMenu;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysRole.sysRole;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysRoleAuth.sysRoleAuth;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysUserRole.sysUserRole;

@Service
@RequiredArgsConstructor
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
