package io.github.xiechanglei.lan.base.rbac.init;

import io.github.xiechanglei.lan.base.rbac.entity.SysUser;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 该类用于获取所有的Entity，并且判断是否存在自定义的UserEntity
 * 如果存在，则使用自定义的UserEntity
 * 否则使用默认的UserEntity-->io.github.xiechanglei.lan.base.rbac.entity.SysUser
 */
@Component
@Getter
public class LanBaseJpaEntityManager implements ApplicationContextAware {
    private Set<Class<?>> allEntityClasses;

    /**
     * 获取所有的Entity
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        EntityScanner entityScanner = new EntityScanner(applicationContext);
        try {
            allEntityClasses = entityScanner.scan(Entity.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否存在自定义的UserEntity
     * 过滤所有的Entity，判断其父类是否为SysUserAuth，且是否不是SysUser类，如果是返回ture，表示有自定义的UserEntity
     */
    @SuppressWarnings("rawtypes")
    public void ifHasCustomUserEntity(Consumer<? super Class> consumer) {
        allEntityClasses.stream()
                .filter(cls-> SysUserAuth.class.isAssignableFrom(cls) && cls!= SysUser.class)
                .findFirst().ifPresent(consumer);
    }
}
