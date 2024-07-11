package io.github.xiechanglei.lan.rbac.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.rbac.entity.log.SysLog;
import io.github.xiechanglei.lan.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.rbac.repo.LanSysLogRepository;
import io.github.xiechanglei.lan.web.log.LanApiLogHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 系统日志的默认实现，将日志存储在数据库中
 */
@Component
@Log4j2
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable-log", havingValue = "true", matchIfMissing = true)
public class LanApiLogDataStoreHandler implements LanApiLogHandler {
    private final LanSysLogRepository lanBaseSysLogRepository;
    private List<SysLog> cacheLogList = new LinkedList<>();

    @Scheduled(fixedRate = 5000)
    public void startSaveTask() {
        if (!cacheLogList.isEmpty()) {
            lanBaseSysLogRepository.saveAll(getAllLogs());
        }
    }

    public List<SysLog> getAllLogs() {
        List<SysLog> allLogs = cacheLogList;
        cacheLogList = new LinkedList<>();
        return allLogs;
    }

    public void addLog(SysLog sysLog) {
        cacheLogList.add(sysLog);
    }

    @Override
    public void handle(String name, String ip, String path, Map<String, Object> params) {
        try {
            // 获取当前登录用户id
            SysLog sysLog = new SysLog();
            sysLog.setLogTitle(name);
            sysLog.setLogAddress(ip);
            sysLog.setLogPath(path);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(params);
            if (jsonString.length() > 200) {
                throw BusinessException.of("params 长度超过限制");
            }
            sysLog.setParams(jsonString);
            TokenInfo tokenInfo = TokenContextHolder.getCurrentTokenInfo();
            if (tokenInfo != null) {
                sysLog.setUserId(tokenInfo.getUserId());
            }
            addLog(sysLog);
        } catch (Exception e) {
            log.error("日志入库失败", e);
        }
    }
}
