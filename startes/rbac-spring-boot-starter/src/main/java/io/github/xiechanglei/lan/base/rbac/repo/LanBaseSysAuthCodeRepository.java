package io.github.xiechanglei.lan.base.rbac.repo;

import io.github.xiechanglei.lan.base.rbac.entity.SysAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysAuthCodeRepository  权限字符字典表
 */
public interface LanBaseSysAuthCodeRepository extends JpaRepository<SysAuthCode, String>{
}
