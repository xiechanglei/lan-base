package io.github.xiechanglei.lan.web.log;

import io.github.xiechanglei.lan.web.util.RequestHelper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 增加日志处理器，标记了@ApiLog的方法会被记录日志
 */
@Aspect
@Component
@Log4j2
@ConditionalOnBean(LanApiLogHandler.class)
public class LanApiLogAop implements ApplicationContextAware {
    // 初始化的时候找到日志处理器，1.找不到就不再去管理日志 2.提供一个默认的日志处理器

    private Collection<LanApiLogHandler> logHandlers;

    //    @ApiLog(value = "接口名称", params = {"userId", "roleId"})
    @Before("@annotation(io.github.xiechanglei.lan.web.log.ApiLog)")
    public void apiLog(JoinPoint joinPoint) {
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
        this.logHandlers.forEach(handler -> handler.handle(apiName, currentRequestIp, path, paramsMap));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有实现了LanApiLogHandler接口的bean
        this.logHandlers = applicationContext.getBeansOfType(LanApiLogHandler.class).values();
    }
}
