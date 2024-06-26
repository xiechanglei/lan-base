package io.github.xiechanglei.lan.base.rbac.init.data;


import io.github.xiechanglei.lan.base.rbac.annotation.AuthCode;
import io.github.xiechanglei.lan.base.rbac.annotation.AuthModule;
import io.github.xiechanglei.lan.base.rbac.entity.SysAuthCode;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysAuthCodeRepository;
import io.github.xiechanglei.lan.base.rbac.util.DataUpdaterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 初始化authCode
 * 1.获取当前项目中所有authcode，
 * 2.从数据库中获取所有的authcode,做比较
 * 3.将新增的authcode插入到数据库中
 * 4.将删除的authcode从数据库中删除
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class LanBaseRbacAuthCodeInitiation {

    private final LanBaseSysAuthCodeRepository lanBaseSysAuthCodeRepository;

    /**
     * 初始化authCode
     * 调用封装的DataUpdaterUtil.update()方法对数据库中的authCode进行比较更新
     * 实际上就是两个List集合的比较，一个是数据库中的authCode，一个是从当前项目中获取的authCode
     */
    public void initData(ApplicationContext applicationContext) {
        log.info("更新权限字符字典表...");
        List<SysAuthCode> allAuthCode = getAllAuthCode(applicationContext);
        DataUpdaterUtil.update(lanBaseSysAuthCodeRepository, allAuthCode);
    }

    /**
     * 获取当前项目中所有@AuthModule,@AuthCode注解的值
     */
    private List<SysAuthCode> getAllAuthCode(ApplicationContext applicationContext) {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(AuthModule.class);
        List<SysAuthCode> allAuthCode = new ArrayList<>();
        beansWithAnnotation.values().forEach(bean -> {
            AuthModule moduleAnnotation = bean.getClass().getAnnotation(AuthModule.class);
            Field[] declaredFields = bean.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(AuthCode.class)) {
                    AuthCode authCodeAnnotation = declaredField.getAnnotation(AuthCode.class);
                    try {
                        String authCode = declaredField.get(bean).toString();
                        allAuthCode.add(new SysAuthCode(authCode, authCodeAnnotation.value(), moduleAnnotation.value()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        return allAuthCode;
    }
}
