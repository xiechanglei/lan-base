package io.github.xiechanglei.lan.base.web.log;

import io.github.xiechanglei.lan.base.web.util.RequestHelper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Aspect
@Component
@Log4j2
public class LanBaseApiLogAop {
    // 初始化的时候找到日志处理器，1.找不到就不再去管理日志 2.提供一个默认的日志处理器

    private final LanBaseApiLogHandler lanBaseApiLogHandler;

    public LanBaseApiLogAop(@Autowired(required = false) LanBaseApiLogHandler lanBaseApiLogHandler) {
        this.lanBaseApiLogHandler = lanBaseApiLogHandler;
    }

    //    @ApiLog(value = "接口名称", params = {"userId", "roleId"})
    @Before("@annotation(io.github.xiechanglei.lan.base.web.log.ApiLog)")
    public void apiLog(JoinPoint joinPoint) {
        if (lanBaseApiLogHandler == null) {
            return;
        }
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ApiLog apiLog = signature.getMethod().getAnnotation(ApiLog.class);
        HttpServletRequest request = RequestHelper.getCurrentRequest();
        // 获取注解上的值
        String apiName = apiLog.value();
        // 请求路径
        String path = request.getRequestURI();
        // 请求地址
        String currentRequestIp = RequestHelper.getCurrentRequestIp();
        // 获取参数
        Map<String, Object> paramsMap = new HashMap<>();
        for (String param : apiLog.params()) {
            paramsMap.put(param, request.getParameter(param));
        }
        try {
            lanBaseApiLogHandler.handle(apiName, currentRequestIp, path, paramsMap);
        } catch (Exception e) {
            log.error("handle log failed!");
        }
    }
}
