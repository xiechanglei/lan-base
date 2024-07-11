package io.github.xiechanglei.lan.rbac.service;

import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.rbac.entity.base.SysRole;
import io.github.xiechanglei.lan.rbac.entity.base.SysRoleAuth;
import io.github.xiechanglei.lan.rbac.internal.constans.BusinessError;
import io.github.xiechanglei.lan.rbac.repo.LanSysResourceCodeRepository;
import io.github.xiechanglei.lan.rbac.repo.LanSysRoleAuthRepository;
import io.github.xiechanglei.lan.rbac.repo.LanSysRoleRepository;
import io.github.xiechanglei.lan.rbac.repo.LanSysUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanSysRoleService {
    private final LanSysUserRoleRepository lanSysUserRoleRepository;
    private final LanSysRoleRepository lanSysRoleRepository;
    private final LanSysRoleAuthRepository lanSysRoleAuthRepository;
    private final LanSysResourceCodeRepository lanSysResourceCodeRepository;

    public List<SysRole> findByUserId(String id) {
        return lanSysRoleRepository.findByUserId(id);
    }


    /**
     * 分页查询所有角色信息，角色名存在则根据角色名查询，否则查询所有角色
     * pageParam 分页参数
     * roleName 角色名称
     *
     * @return 角色
     */
    public Page<SysRole> searchRole(PageRequest pageRequest, String roleName) {
        return lanSysRoleRepository.findAllByRoleName(pageRequest, roleName);
    }

    /**
     * 创建角色
     */
    public void createRole(String roleName) {
        if (lanSysRoleRepository.existsByRoleName(roleName)) {
            throw BusinessError.ROLE.ROLE_EXISTS;
        }
        lanSysRoleRepository.save(SysRole.creatRole(roleName));
    }

    /**
     * 根据角色ID查询角色详情
     *
     * @param roleId 角色id
     */
    public SysRole getRoleById(String roleId) {
        return lanSysRoleRepository.findById(roleId).orElseThrow(() -> BusinessError.ROLE.ROLE_NOT_EXISTS);
    }

    /**
     * 检查是否可以编辑角色，如果可以编辑则返回角色
     * 目前判断如果配置了系统自动管理管理员模式，则不能编辑管理员角色
     *
     * @param roleId 角色id
     * @return 角色
     * @throws BusinessException 如果角色不存在或者不能编辑则抛出异常
     */
    private SysRole getRoleIfCanEdit(String roleId) throws BusinessException {
        SysRole rbacAuthRole = lanSysRoleRepository.findById(roleId).orElseThrow(() -> BusinessError.ROLE.ROLE_NOT_EXISTS);
        if (rbacAuthRole.isAdmin()) {
            throw BusinessError.ROLE.ROLE_CAN_NOT_OPERATE;
        }
        return rbacAuthRole;
    }

    /**
     * 更新角色
     */
    public void editRole(String roleName, String roleId, String roleRemark) {
        // id查询角色是否可编辑，非空
        SysRole sysRole = getRoleIfCanEdit(roleId);
        // 判断修改的名字是否存在
        if (lanSysRoleRepository.existsByRoleNameAndIdNot(roleName, roleId)) {
            throw BusinessError.ROLE.ROLE_EXISTS;
        }
        sysRole.setRoleName(roleName);
        sysRole.setRoleRemark(roleRemark);
        lanSysRoleRepository.save(sysRole);
    }

    /**
     * 修改角色状态
     *
     * @param roleId 角色id
     */
    public void changeRoleStatus(String roleId, SysRole.RoleStatus roleStatus) {
        SysRole sysRole = getRoleIfCanEdit(roleId);
        sysRole.setRoleStatus(roleStatus);
        lanSysRoleRepository.save(sysRole);
    }


    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    public void deleteRole(String roleId) {
        //查询角色是否被授权
        if (lanSysUserRoleRepository.existsByRoleId(roleId)) {
            throw BusinessError.ROLE.ROLE_CAN_NOT_DELETE;
        }
        //删除角色信息
        lanSysRoleRepository.delete(getRoleIfCanEdit(roleId));
        //删除角色授权的资源信息
        lanSysRoleAuthRepository.deleteByRoleId(roleId);
    }

    /**
     * 查询角色权限
     * lanBaseJpaEntityManager
     *
     * @param roleId 角色id
     */
    public List<String> loadRoleResource(String roleId) {
        return lanSysResourceCodeRepository.findAllResourceByRoleId(roleId);
    }


    /**
     * 设置角色权限
     */
    public void grantResource(String roleId, String[] resourceIds) {
        getRoleIfCanEdit(roleId);
        lanSysRoleAuthRepository.deleteByRoleId(roleId);
        List<SysRoleAuth> roleAuths = Arrays.stream(resourceIds)
                .map(resourceId -> SysRoleAuth.createRoleAuth(roleId, resourceId)).collect(Collectors.toList());
        lanSysRoleAuthRepository.saveAll(roleAuths);
    }


    /**
     * 查询用户id，是否拥有管理员角色
     *
     * @param userId 用户id
     */
    public boolean hasAdminRole(String userId) {
        return lanSysRoleRepository.countAdminRoleByUserId(userId) > 0;
    }

}