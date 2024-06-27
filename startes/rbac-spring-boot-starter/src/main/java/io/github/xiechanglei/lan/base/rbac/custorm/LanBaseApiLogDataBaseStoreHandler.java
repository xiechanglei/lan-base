package io.github.xiechanglei.lan.base.rbac.custorm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.base.rbac.entity.SysLog;
import io.github.xiechanglei.lan.base.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysLogRepository;
import io.github.xiechanglei.lan.base.web.log.LanBaseApiLogHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class LanBaseApiLogDataBaseStoreHandler implements LanBaseApiLogHandler {
    private final LanBaseSysLogRepository lanBaseSysLogRepository;
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
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
