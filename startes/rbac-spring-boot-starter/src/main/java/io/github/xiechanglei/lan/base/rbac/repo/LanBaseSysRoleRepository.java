package io.github.xiechanglei.lan.base.rbac.repo;

import io.github.xiechanglei.lan.base.rbac.entity.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * LanBaseSysRoleRepository  角色表
 */
public interface LanBaseSysRoleRepository extends JpaRepository<SysRole, String> {

    /**
     * 根据角色名查询角色 是否存在
     *
     * @param roleName 角色名
     * @param id       角色id
     * @return 是否存在
     */
    boolean existsByRoleNameAndIdNot(String roleName, String id);

    /**
     * 根据角色名查询角色 是否存在
     *
     * @param roleName 角色名
     * @return 是否存在
     */
    boolean existsByRoleName(String roleName);

    /**
     * 根据用户id查询角色
     *
     * @param id 用户id
     */
    @Query("select r from SysRole r ,SysUserRole ur where r.id = ur.roleId and ur.userId = ?1")
    List<SysRole> findByUserId(String id);


    /**
     * 查询用户是否关联管理员角色
     */
    @Query("select count(r) from SysRole r ,SysUserRole ur where r.id = ur.roleId and ur.userId = ?1 and r.isAdmin = true ")
    int countAdminRoleByUserId(String id);

    /**
     * 查询所有的管理员角色
     *
     * @return 管理员角色
     */
    @Query("select r from SysRole r where r.isAdmin = true ")
    List<SysRole> findAllAdminRole();


    /**
     * 分页查询所有角色信息，角色名存在则根据角色名查询，否则查询所有角色
     *
     * @param pageRequest 分页参数
     * @param roleName    角色名
     * @return 角色
     */
    @Query("select r from SysRole r where r.roleName like %?1%")
    Page<SysRole> findAllByRoleName(PageRequest pageRequest, String roleName);
}

