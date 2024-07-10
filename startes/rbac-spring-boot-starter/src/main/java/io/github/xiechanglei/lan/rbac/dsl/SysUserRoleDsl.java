package io.github.xiechanglei.lan.rbac.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.rbac.entity.base.QSysRole.sysRole;
import static io.github.xiechanglei.lan.rbac.entity.base.QSysUserRole.sysUserRole;

/**
 * 关于构建用户角色的dsl
 */
@Service
@RequiredArgsConstructor
public class SysUserRoleDsl {
    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 查询拥有管理员的用户的个数
     */
    public List<String> getAllAdminUserId() {
        return jpaQueryFactory.select(sysUserRole.userId).from(sysUserRole)
                .innerJoin(sysRole).on(sysUserRole.roleId.eq(sysRole.id))
                .where(sysRole.isAdmin.eq(true)).fetch();
    }


}
