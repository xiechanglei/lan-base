package io.github.xiechanglei.lan.rbac.service;

import io.github.xiechanglei.lan.rbac.entity.SysLog;
import io.github.xiechanglei.lan.rbac.repo.LanSysLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanSysLogService {
    private final LanSysLogRepository lanBaseSysLogRepository;

    public Page<SysLog> searchLog(PageRequest pageRequest, String word) {
        return lanBaseSysLogRepository.findByLogTitleLike("%" + word + "%", pageRequest);
    }
}