package io.github.xiechanglei.lan.base.rbac.service;

import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LanBaseSysLogService {
    private final LanBaseSysLogRepository lanBaseSysLogRepository;
}