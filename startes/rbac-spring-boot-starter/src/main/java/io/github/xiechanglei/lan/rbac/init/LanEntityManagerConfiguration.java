package io.github.xiechanglei.lan.rbac.init;

import io.github.xiechanglei.lan.utils.collections.ArrayHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

/**
 * 自定义的LocalContainerEntityManagerFactoryBean,用来创建EntityManagerFactory
 */
@Configuration
@RequiredArgsConstructor
public class LanEntityManagerConfiguration {
    private final JpaProperties jpaProperties;
    private final DataSource dataSource;
    private final LanJpaEntityManager lanJpaEntityManager;

    @Bean
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        String[] rbacEntityPackages = lanJpaEntityManager.getRbacEntityPackages();
        String[] allEntityScanPackages = ArrayHelper.concat(lanJpaEntityManager.getAllEntityScanPackages(), rbacEntityPackages);

        // 临时创建一个factoryBean，用于初始化rbac相关的数据库表结构
        LocalContainerEntityManagerFactoryBean tempFactoryBean = buildFactoryBean(builder, rbacEntityPackages, new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
            setShowSql(true);
        }});
        tempFactoryBean.destroy();

        // 创建真正的factoryBean
        return buildFactoryBean(builder, allEntityScanPackages, new HibernateJpaVendorAdapter() {{
            setGenerateDdl(jpaProperties.isGenerateDdl());
            setShowSql(jpaProperties.isShowSql());
        }});
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