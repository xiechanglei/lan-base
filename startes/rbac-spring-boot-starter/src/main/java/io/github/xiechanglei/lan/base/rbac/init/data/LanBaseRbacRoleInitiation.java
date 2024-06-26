package io.github.xiechanglei.lan.base.rbac.init.data;

import io.github.xiechanglei.lan.base.rbac.entity.SysRole;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysResourceCodeRepository;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysRoleRepository;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysUserAuthService;
import io.github.xiechanglei.lan.base.rbac.service.LanBaseSysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 15.获取所有的角色的资源id去重，
 * 16.从数据库中获取所有的角色的资源id去重，做比较
 * 18.将不存的资源删掉  delete from role_resource_ref where  resource_id = ?
 * <p>
 * 19.创建长及管理员角色，和用户
 * 20.创建管理员角色和用户
 */
@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseRbacRoleInitiation {
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;
    private final LanBaseSysRoleRepository sysRoleRepository;
    private final LanBaseSysUserAuthService sysUserAuthService;
    private final LanBaseSysUserRoleService sysUserRoleService;
    private final LanBaseSysResourceCodeRepository sysResourceCodeRepository;

    /**
     * 初始化角色与用户的数据
     *
     * @param allResourceIds 所有的角色的资源，用于删除在角色与资源关系表中不存在的资源
     */
    public void initData(List<String> allResourceIds) {
        log.info("更新角色以及用户数据...");
        deleteResourceNotExist(allResourceIds);
        SysRole adminRole = createOrGetAdminRole();
        createAdminUser(adminRole.getId());
    }

    /**
     * 检查并创建超级管理员角色
     *
     * @return 超级管理员角色
     */
    private SysRole createOrGetAdminRole() {
        List<SysRole> adminsRoles = sysRoleRepository.findAllAdminRole();
        if (adminsRoles.isEmpty()) {
            log.info("创建超级管理员角色");
            SysRole superAdminRole = SysRole.createAdmin(lanBaseRbacConfigProperties.getRoleAdminName());
            sysRoleRepository.save(superAdminRole);
            return superAdminRole;
        } else {
            return adminsRoles.get(0);
        }
    }

    /**
     * 创建超级管理员用户
     *
     * @param adminRoleId 超级管理员角色id
     */
    private void createAdminUser(String adminRoleId) {
        if (lanBaseRbacConfigProperties.isCreateAdmin() && !sysUserRoleService.existsByRoleId(adminRoleId)) {
            try {
                SysUserAuth supperAdmin = sysUserAuthService.getUserEntityClass().newInstance().buildAdmin();
                log.info("创建超级管理员用户");
                if (sysUserAuthService.existsByUsername(supperAdmin.getUserName())) {
                    log.info("超级管理员用户名已存在");
                } else {
                    sysUserAuthService.add(supperAdmin);
                    // 绑定超级管理员角色
                    sysUserRoleService.bindRole(supperAdmin.getId(), adminRoleId);
                }
            } catch (Exception e) {
                log.info("创建超级管理员用户失败");
            }
        }
    }

    /**
     * 删除在角色与资源关系表中不存在的资源
     *
     * @param allResourceIds 所有的角色的资源
     */
    private void deleteResourceNotExist(List<String> allResourceIds) {
        if (allResourceIds == null || allResourceIds.isEmpty()) {
            sysResourceCodeRepository.deleteAll();
        } else {
            // 从数据库中获取所有的角色的资源id去重
            List<String> allRoleResourceIds = sysResourceCodeRepository.findAllResourceCode();
            // 获取allRoleResourceIds中不在allResourceIds中的资源
            allRoleResourceIds.removeAll(allResourceIds);
            // 删除在角色与资源关系表中不存在的资源
            allRoleResourceIds.forEach(sysResourceCodeRepository::deleteByResourceId);
        }
    }
}
