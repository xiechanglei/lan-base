package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.SysUser;
import io.github.xiechanglei.lan.rbac.entity.SysUserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    /**
     * 根据角色id 查询关联下所有用户
     * @param pageRequest 分页参数
     * @param roleId 角色id
     * @return 用户
     */
    @Query("select u from SysUser u,SysUserRole ur where u.id = ur.userId and ur.roleId = ?1")
    Page<SysUser> findByRoleId(PageRequest pageRequest,String roleId);
}