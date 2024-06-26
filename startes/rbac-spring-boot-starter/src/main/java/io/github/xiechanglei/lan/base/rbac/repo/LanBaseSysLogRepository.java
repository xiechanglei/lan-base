package io.github.xiechanglei.lan.base.rbac.repo;

import io.github.xiechanglei.lan.base.rbac.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysLogRepository 日志表
 */
public interface LanBaseSysLogRepository extends JpaRepository<SysLog, String> {
}
