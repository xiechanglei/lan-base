package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.SysResourceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * LanBaseSysResourceCodeRepository  资源权限字符字典表
 */
public interface LanSysResourceCodeRepository extends JpaRepository<SysResourceCode, String> {

    /**
     * 校验用户访问权限
     *
     * @param userId      用户id
     * @param permissions 权限集合
     */
    @Query("select distinct rc.authCode from " +
            "SysUserRole ur, " +
            "SysRoleAuth ra, " +
            "SysResourceCode rc " +
            "where ur.roleId = ra.roleId and ra.resourceId = rc.resourceId and ur.userId = ?1 and rc.authCode in ?2")
    List<String> findUserAuthCodeIn(String userId, String[] permissions);

    /**
     * 根据角色id获取资源id关联查询所有资源权限数据
     *
     * @param roleId 角色id
     */
    @Query("select ra.resourceId from SysRoleAuth ra where ra.roleId = ?1")
    List<String> findAllResourceByRoleId(String roleId);

    /**
     * 获取所有的资源id
     */
    @Query("select distinct rc.resourceId from SysResourceCode rc")
    List<String> findAllResourceCode();

    /**
     * 根据资源id删除资源权限数据
     */
    void deleteByResourceId(String resourceId);
}
