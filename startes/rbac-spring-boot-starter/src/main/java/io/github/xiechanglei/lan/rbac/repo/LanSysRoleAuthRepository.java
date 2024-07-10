package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.base.SysRoleAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface LanSysRoleAuthRepository extends JpaRepository<SysRoleAuth, String> {
    @Transactional
    @Modifying
    @Query("delete from SysRoleAuth where roleId=?1")
    void deleteByRoleId(String roleId);
}
