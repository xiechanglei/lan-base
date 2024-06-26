package io.github.xiechanglei.lan.base.rbac.custorm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.xiechanglei.lan.base.beans.exception.BusinessException;
import io.github.xiechanglei.lan.base.rbac.entity.SysLog;
import io.github.xiechanglei.lan.base.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysLogRepository;
import io.github.xiechanglei.lan.base.web.log.LanBaseApiLogHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Log4j2
@RequiredArgsConstructor
public class LanBaseApiLogDataBaseStoreHandler implements LanBaseApiLogHandler {
    private final LanBaseSysLogRepository lanBaseSysLogRepository;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // params json 去存， 字段长度设计问题 ，200，字段长度过长，就不存了
    // 延迟的批量存储
    // 如何让子系统接管流程，
    // TODO 优化
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
            // 延时5秒存数据库
            executor.schedule(() -> {
                lanBaseSysLogRepository.save(sysLog);
            }, 5000, TimeUnit.MILLISECONDS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
