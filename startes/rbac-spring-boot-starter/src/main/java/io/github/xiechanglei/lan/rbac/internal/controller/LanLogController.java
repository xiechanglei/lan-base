package io.github.xiechanglei.lan.rbac.internal.controller;

import io.github.xiechanglei.lan.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.rbac.entity.log.SysLog;
import io.github.xiechanglei.lan.rbac.internal.permission.InternalLogAuthCodeManager;
import io.github.xiechanglei.lan.rbac.service.LanSysLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 角色相关的接口
 */
@Validated
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = {"internal-api", "enable-log"}, havingValue = "true", matchIfMissing = true)
public class LanLogController {
    private final LanSysLogService lanSysLogService;


    /**
     * 分页查询所有日志, 支持时间范围查询,value日志标题,logPath请求路径,请求IP地址logAddress
     */
    @NeedAuth(InternalLogAuthCodeManager.QUERY)
    @RequestMapping("/rbac/log/search")
    public Page<SysLog> searchLog(PageRequest pageRequest, String ip, String title, Date startTime, Date endTime) {
        return lanSysLogService.searchLog(pageRequest, ip, title, startTime, endTime);
    }
}
