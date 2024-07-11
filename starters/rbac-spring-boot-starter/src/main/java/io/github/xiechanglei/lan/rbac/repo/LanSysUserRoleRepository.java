package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.base.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LanSysUserRoleRepository extends JpaRepository<SysUserRole, String> {

    /**
     * 判断用户是否拥有某个角色
     */
    boolean existsByUserIdAndRoleId(String userId, String roleId);


    /**
     * 是否存在指定角色的用户关联
     */
    boolean existsByRoleId(String roleId);

    /**
     * 根据用户id删除用户的角色关联
     * @param userId 用户id
     */
    @Transactional
    void deleteByUserId(String userId);

}