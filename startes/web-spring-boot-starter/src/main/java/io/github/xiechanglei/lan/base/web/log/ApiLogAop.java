package io.github.xiechanglei.lan.base.web.log;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
public class ApiLogAop {
    // 初始化的时候找到日志处理器，1.找不到就不再去管理日志 2.提供一个默认的日志处理器

    @ApiLog(value = "接口名称", params = {"userId", "roleId"})
    @Around("@annotation(io.github.xiechanglei.lan.base.web.log.ApiLog)")
    public Object apiLog() {
        return null;
    }

}
