package io.github.xiechanglei.lan.rbac.internal.controller;

import io.github.xiechanglei.lan.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.rbac.entity.SysLog;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalLogAuthCodeManager;
import io.github.xiechanglei.lan.rbac.service.LanSysLogService;
import io.github.xiechanglei.lan.web.log.ApiLog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 角色相关的接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "internal-api", havingValue = "true", matchIfMissing = true)
public class LanLogController {
    private final LanSysLogService lanSysLogService;

    /**
     * 查询日志
     */
    @ApiLog(value = "查询日志", params = {"word"})
    @NeedAuth(InternalLogAuthCodeManager.QUERY)
    @RequestMapping("/rbac/log/query")
    public Page<SysLog> searchLog(PageRequest pageRequest, @RequestParam(required = false, defaultValue = "") String word) {
        return lanSysLogService.searchLog(pageRequest, word);
    }

}
