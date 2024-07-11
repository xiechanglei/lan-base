package io.github.xiechanglei.lan.rbac.swit;

import io.github.xiechanglei.lan.rbac.init.LanJpaEntityManager;
import io.github.xiechanglei.lan.utils.collections.ArrayHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * 放在一个单独的配置类中，主要是为了当用户不需要rbac功能时，可以通过配置关闭lan.rbac.enable,可以使ComponentScan注解失效，
 * 这样就不用在所有的类上都加上@ConditionalOnProperty注解了
 */
@Configuration
@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories("io.github.xiechanglei.lan.rbac")
@ComponentScan("io.github.xiechanglei.lan.rbac")
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class LanRbacDynamicConfig {
    private final DataSource dataSource;
    private final LanJpaEntityManager lanJpaEntityManager;
    private final EntityManagerFactoryBuilder builder;
    private final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;

    @PostConstruct
    public void initTables() {
        String[] rbacEntityPackages = lanJpaEntityManager.getRbacEntityPackages();
        String[] allEntityScanPackages = ArrayHelper.concat(lanJpaEntityManager.getAllEntityScanPackages(), rbacEntityPackages);

        // 临时创建一个factoryBean，用于初始化rbac相关的数据库表结构
        LocalContainerEntityManagerFactoryBean tempFactoryBean = buildFactoryBean(builder, rbacEntityPackages, new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
            setShowSql(true);
        }});
        tempFactoryBean.destroy();
        localContainerEntityManagerFactoryBean.setPackagesToScan(allEntityScanPackages);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
    }

    private LocalContainerEntityManagerFactoryBean buildFactoryBean(EntityManagerFactoryBuilder builder, String[] allEntityScanPackages, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean factoryBean = builder.dataSource(dataSource)
                .persistenceUnit("default")
                .build();
        factoryBean.setPackagesToScan(allEntityScanPackages);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }
}
