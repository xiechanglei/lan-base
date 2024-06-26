package io.github.xiechanglei.lan.rbac.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.rbac.entity.SysMenuFc;
import io.github.xiechanglei.lan.rbac.entity.SysRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.rbac.entity.QSysMenuFc.sysMenuFc;
import static io.github.xiechanglei.lan.rbac.entity.QSysRole.sysRole;
import static io.github.xiechanglei.lan.rbac.entity.QSysRoleAuth.sysRoleAuth;
import static io.github.xiechanglei.lan.rbac.entity.QSysUserRole.sysUserRole;


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
