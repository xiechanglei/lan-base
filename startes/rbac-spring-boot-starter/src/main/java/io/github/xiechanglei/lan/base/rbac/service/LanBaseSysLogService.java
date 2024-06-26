package io.github.xiechanglei.lan.base.rbac.service;

import io.github.xiechanglei.lan.base.rbac.entity.SysLog;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanBaseSysLogService {
    private final LanBaseSysLogRepository lanBaseSysLogRepository;

    public Page<SysLog> searchLog(PageRequest pageRequest, String word) {
        return lanBaseSysLogRepository.findByLogTitleLike("%" + word + "%", pageRequest);
    }
}