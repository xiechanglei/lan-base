package io.github.xiechanglei.lan.base.rbac.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.xiechanglei.lan.base.rbac.entity.QSysRole.sysRole;
import static io.github.xiechanglei.lan.base.rbac.entity.QSysUser.sysUser;
import static io.github.xiechanglei.lan.base.rbac.entity.QSysUserRole.sysUserRole;

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
        return jpaQueryFactory.select(sysUser.id).from(sysUser)
                .innerJoin(sysUserRole).on(sysUser.id.eq(sysUserRole.userId))
                .innerJoin(sysRole).on(sysUserRole.roleId.eq(sysRole.id))
                .where(sysUser.userStatus.eq(SysUserAuth.UserStatus.ENABLE)
                        .and(sysRole.isAdmin.eq(true))).fetch();
    }


}
