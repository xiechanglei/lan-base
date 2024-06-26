package io.github.xiechanglei.lan.base.rbac.aop;

import io.github.xiechanglei.lan.base.rbac.properties.LanBaseRbacConfigProperties;
import io.github.xiechanglei.lan.base.utils.collections.ArrayHelper;
import io.github.xiechanglei.lan.base.rbac.annotation.NeedAuth;
import io.github.xiechanglei.lan.base.rbac.custorm.PermissionService;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import io.github.xiechanglei.lan.base.rbac.provide.TokenContextHolder;
import io.github.xiechanglei.lan.base.rbac.provide.UserContextHolder;
import io.github.xiechanglei.lan.base.rbac.service.DefaultPermissionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 权限过滤切面
 * 1. 判断是否开启权限过滤
 * LanBaseRbacConfigProperties.isFilterAuth()
 * 2. 获取需要过滤的最小权限单元，如"rbac:menu:query"
 * 3. 检查当前用户是否拥有该权限单元
 * 4. 可实现permissionService自己重写权限逻辑,默认使用DefaultPermissionService
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseNeedAuthAop {
    private final LanBaseRbacConfigProperties lanBaseRbacConfigProperties;
    private final PermissionService permissionService;
    private final DefaultPermissionService defaultPermissionService;

    public LanBaseNeedAuthAop(LanBaseRbacConfigProperties lanBaseRbacConfigProperties,
                              @Autowired(required = false) PermissionService permissionService,
                              DefaultPermissionService defaultPermissionService) {
        this.lanBaseRbacConfigProperties = lanBaseRbacConfigProperties;
        this.permissionService = permissionService;
        this.defaultPermissionService = defaultPermissionService;
    }

    /**
     * 权限过滤逻辑
     * 1. 判断是否开启权限过滤
     * 2. 获取需要过滤的最小权限单元
     * 3. 检查当前用户是否拥有该权限单元
     */
    @Around("@annotation(io.github.xiechanglei.lan.base.rbac.annotation.NeedAuth)")
    public Object needAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        if (lanBaseRbacConfigProperties.isFilterAuth()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String[] permissions = signature.getMethod().getAnnotation(NeedAuth.class).value();
            // 去重
            permissions = ArrayHelper.distinct(permissions);
            //判断是否存在PermissionService的实现类，如果存在，则是根据PermissionService的实现类来判断是否有权限，
            // 否则使用默认的DefaultPermissionService，这里可实现permissionService自己重写权限逻辑
            if (permissionService == null) {
                defaultPermissionService.checkPermission(TokenContextHolder.getCurrentTokenInfo(), permissions);
            } else {
                SysUserAuth sysUserAuth = permissionService.checkPermission(TokenContextHolder.getCurrentTokenInfo(), permissions);
                UserContextHolder.setCurrentUser(sysUserAuth);
            }
        }
        return joinPoint.proceed();
    }
}
