package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.log.SysLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysLogRepository 日志表
 */
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable-log", havingValue = "true", matchIfMissing = true)
public interface LanSysLogRepository extends JpaRepository<SysLog, String> {
    Page<SysLog> findByLogTitleLike(String title, PageRequest page);
}
