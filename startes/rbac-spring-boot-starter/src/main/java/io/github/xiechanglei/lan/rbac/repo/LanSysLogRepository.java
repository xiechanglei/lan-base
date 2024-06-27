package io.github.xiechanglei.lan.rbac.repo;

import io.github.xiechanglei.lan.rbac.entity.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * LanBaseSysLogRepository 日志表
 */
public interface LanSysLogRepository extends JpaRepository<SysLog, String> {
    Page<SysLog> findByLogTitleLike(String title, PageRequest page);
}
