package io.github.xiechanglei.lan.rbac.init;

import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import io.github.xiechanglei.lan.rbac.entity.log.SysLog;
import io.github.xiechanglei.lan.rbac.entity.user.SysUser;
import io.github.xiechanglei.lan.rbac.properties.LanRbacConfigProperties;
import io.github.xiechanglei.lan.lang.collections.ArrayHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

/**
 * 该类用于获取所有的Entity，并且判断是否存在自定义的UserEntity
 * 如果存在，则使用自定义的UserEntity
 * 否则使用默认的UserEntity-->io.github.xiechanglei.lan.base.rbac.entity.SysUser
 */
@Component
@RequiredArgsConstructor
public class LanJpaEntityManager implements ApplicationContextAware {

    private final LanRbacConfigProperties lanRbacConfigProperties;

    @Getter
    private boolean hasCustomUserEntity = false;

    @Getter
    private Class<? extends SysUserAuth> userEntityClass = SysUser.class;

    @Getter
    private String[] rbacEntityPackages;

    @Getter
    private String[] allEntityScanPackages;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initUserEntityClass(applicationContext);
        initRbacEntityPackages();
        initAllEntityScanPackages(applicationContext);
    }


    /**
     * 找出自定义的UserEntity
     *
     * @param applicationContext 上下文
     */
    @SuppressWarnings("all")
    private void initUserEntityClass(ApplicationContext applicationContext) {
        try {
            EntityScanner entityScanner = new EntityScanner(applicationContext);
            Set<Class<?>> allEntityClasses = entityScanner.scan(Entity.class);
            allEntityClasses.stream().filter(this::isSysUserAuth).findFirst().ifPresent(cls -> {
                hasCustomUserEntity = true;
                userEntityClass = (Class<? extends SysUserAuth>) cls;
            });
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否符合自定义的UserEntity
     *
     * @param cls 类
     */
    private boolean isSysUserAuth(Class<?> cls) {
        return SysUserAuth.class.isAssignableFrom(cls) && cls != SysUser.class;
    }


    /**
     * 获取rbac需要被扫描的包
     */
    private void initRbacEntityPackages() {
        String[] packages = new String[]{"io.github.xiechanglei.lan.rbac.entity.base"};
        if (lanRbacConfigProperties.isEnableLog()) {
            packages = ArrayHelper.concat(packages, new String[]{SysLog.class.getPackage().getName()});
        }
        if (!hasCustomUserEntity) {
            packages = ArrayHelper.concat(packages, new String[]{SysUser.class.getPackage().getName()});
        }
        rbacEntityPackages = packages;
    }

    /**
     * 获取默认的entity扫描包
     */
    private void initAllEntityScanPackages(ApplicationContext applicationContext) {
        List<String> packages = EntityScanPackages.get(applicationContext).getPackageNames();
        if (packages.isEmpty() && AutoConfigurationPackages.has(applicationContext)) {
            packages = AutoConfigurationPackages.get(applicationContext);
        }
        allEntityScanPackages = packages.toArray(new String[0]);
    }

}
