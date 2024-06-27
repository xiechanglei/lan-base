package io.github.xiechanglei.lan.rbac.init.table;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolverHolder;
import javax.persistence.spi.PersistenceUnitInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 初始化数据库表
 */
@Component
@RequiredArgsConstructor
@Log4j2
public class LanRbacTableInitiation {
    private final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
    private final List<String> managedClassNamesCache = new ArrayList<>();

    /**
     * 自动更新rbac的几个表
     */
    public void createTableIfNotExist() {
        log.info("创建RBAC权限控制相关的表...");
        PersistenceProvider persistenceProvider = getPersistenceProvider();
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo();
        try {
            EntityManagerFactory managerFactory = persistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, getJpaProperties());
            managerFactory.close();
        } finally {
            rollBackManagedClassNames(persistenceUnitInfo);
        }
    }

    /**
     * 获取PersistenceProvider
     */
    private PersistenceProvider getPersistenceProvider() {
        return PersistenceProviderResolverHolder
                .getPersistenceProviderResolver()
                .getPersistenceProviders()
                .stream()
                .filter(p -> p instanceof HibernatePersistenceProvider)
                .findFirst().orElse(null);
    }

    /**
     * 获取PersistenceUnitInfo,并且将managedClassNames中的表数据只留下rbac相关的
     */
    private PersistenceUnitInfo getPersistenceUnitInfo() {
        PersistenceUnitInfo persistenceUnitInfo = entityManagerFactoryBean.getPersistenceUnitInfo();
        assert persistenceUnitInfo != null;
        List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        managedClassNamesCache.clear();
        managedClassNamesCache.addAll(managedClassNames);
        managedClassNamesCache.stream().
                filter(s -> !s.startsWith("io.github.xiechanglei.lan.base.rbac.entity"))
                .forEach(managedClassNames::remove);
        return persistenceUnitInfo;
    }

    /**
     * 获取jpa的配置参数,修改hibernate.hbm2ddl.auto为update,其他参数不变
     */
    public Map<String, Object> getJpaProperties() {
        Map<String, Object> jpaPropertyMap = entityManagerFactoryBean.getJpaPropertyMap();
        Map<String, Object> newProperties = new HashMap<>(jpaPropertyMap);
        newProperties.put("hibernate.hbm2ddl.auto", "update");
//        newProperties.put("hibernate.show_sql", "true");
        return newProperties;
    }

    /**
     * 回滚managedClassNames
     */
    private void rollBackManagedClassNames(PersistenceUnitInfo persistenceUnitInfo) {
        List<String> managedClassNames = persistenceUnitInfo.getManagedClassNames();
        managedClassNames.clear();
        managedClassNames.addAll(managedClassNamesCache);
    }
}
