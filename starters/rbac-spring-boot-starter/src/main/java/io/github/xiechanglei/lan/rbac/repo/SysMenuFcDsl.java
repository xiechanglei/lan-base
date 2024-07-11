package io.github.xiechanglei.lan.rbac.repo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.rbac.entity.base.SysMenuFc;
import io.github.xiechanglei.lan.rbac.entity.base.SysRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.rbac.entity.base.QSysMenuFc.sysMenuFc;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysRole.sysRole;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysRoleAuth.sysRoleAuth;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysUserRole.sysUserRole;


@Service
@RequiredArgsConstructor
public class SysMenuFcDsl {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 根据用户id查询功能
     *
     * @param userId 用户id
     */
    public List<SysMenuFc> findByUserId(String userId) {
        return jpaQueryFactory.select(sysMenuFc).from(sysUserRole, sysRole, sysRoleAuth, sysMenuFc).
                where(sysUserRole.roleId.eq(sysRole.id)
                        .and(sysRole.id.eq(sysRoleAuth.roleId))
                        .and(sysRoleAuth.resourceId.eq(sysMenuFc.fcCode))
                        .and(sysUserRole.userId.eq(userId))
                        .and(sysMenuFc.fcStatus.eq(SysMenuFc.FuncStatus.ENABLE))
                        .and(sysRole.roleStatus.eq(SysRole.RoleStatus.ENABLE)))
                .distinct().fetch();
    }

}
