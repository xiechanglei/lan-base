package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.SysAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysAuthCodeRepository  权限字符字典表
 */
public interface LanSysAuthCodeRepository extends JpaRepository<SysAuthCode, String>{
}
