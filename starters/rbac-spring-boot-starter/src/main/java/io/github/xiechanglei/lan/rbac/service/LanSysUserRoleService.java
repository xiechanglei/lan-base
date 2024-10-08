package io.github.xiechanglei.lan.rbac.service;

import io.github.xiechanglei.lan.rbac.repo.SysUserRoleDsl;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserRole;
import io.github.xiechanglei.lan.rbac.init.LanJpaEntityManager;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.repo.LanSysRoleRepository;
import io.github.xiechanglei.lan.rbac.repo.LanSysUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanSysUserRoleService {
    private final LanSysUserRoleRepository lanSysUserRoleRepository;
    private final SysUserRoleDsl sysUserRoleDsl;
    private final LanSysRoleRepository lanSysRoleRepository;
    private final LanJpaEntityManager lanJpaEntityManager;
    private final EntityManager entityManager;


    /**
     * 绑定角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    public void bindRole(String userId, String roleId) {
        if (!lanSysUserRoleRepository.existsByUserIdAndRoleId(userId, roleId)) {
            lanSysUserRoleRepository.save(SysUserRole.createAuthUserRole(userId, roleId));
        }
    }

    /**
     * 是否存在角色对应的用户
     *
     * @param roleId 角色id
     * @return 是否存在
     */
    public boolean existsByRoleId(String roleId) {
        return lanSysUserRoleRepository.existsByRoleId(roleId);
    }


    /**
     * 0.我是管理员系统里面除了我自己没有管理员了
     * 2.我当前被授权的角色列表里面没有管理员
     *
     * @param userId  用户id
     * @param roleIds 角色id集合
     *                授权应该要接受多个角色id，前端传递的是该用户的所有的角色id，所以操作应该是删除之前旧的数据，然后插入新的数据，
     *                系统应该至少保留一个管理员角色的用户
     */
    public void grantRoles(String userId, String[] roleIds) {
        List<String> roleIdList = Arrays.asList(roleIds); // 将数组转换为List

        List<String> allAdminUserId = sysUserRoleDsl.getAllAdminUserId();
        // 系统仅仅只有当前一个管理员
        if (allAdminUserId.size() == 1 && allAdminUserId.contains(userId)) {
            // 传入的角色中没有管理员角色
            lanSysRoleRepository.findAllAdminRole().stream()
                    .filter(role -> roleIdList.contains(role.getId())).findAny().orElseThrow(() -> BusinessError.USER.USER_ADMIN_ROLE);
        }

        // 删除之前的依赖关系
        lanSysUserRoleRepository.deleteByUserId(userId);

        // 构建新的用户角色列表
        List<SysUserRole> userRoles = roleIdList.stream()
                .map(roleId -> SysUserRole.createAuthUserRole(userId, roleId))
                .collect(Collectors.toList());

        // 批量保存用户角色
        lanSysUserRoleRepository.saveAll(userRoles);
    }
    /**
     * 根据角色id查询用户
     *
     * @param roleId 角色id
     */
    @SuppressWarnings("all")
    public Page<SysUserAuth> getUserByRoleId(String roleId,PageRequest pageRequest) {
        String hql = "select u from " + lanJpaEntityManager.getUserEntityClass().getSimpleName() + " u,SysUserRole ur where u.id = ur.userId and ur.roleId = :roleId";
        String countSql = "select count(u) from SysUser u,SysUserRole ur where u.id = ur.userId and ur.roleId = :roleId";
        List users = entityManager.createQuery(hql)
                .setParameter("roleId", roleId)
                .setMaxResults(pageRequest.getPageSize())
                .setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize())
                .getResultList();
        Long total = (Long) entityManager.createQuery(countSql).setParameter("roleId", roleId).getSingleResult();
        return new PageImpl<>(users, pageRequest, total);
    }
}