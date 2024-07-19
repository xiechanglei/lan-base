package io.github.xiechanglei.lan.rbac.swit;

import io.github.xiechanglei.lan.rbac.init.LanJpaEntityManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * 初始化rbac相关的数据库表结构,为了不影响其他的Entity的管理，这里使用了一个临时的LocalContainerEntityManagerFactoryBean,
     * 在之前的代码中使用localContainerEntityManagerFactoryBean.setPackagesToScan()方法按需来家在我们所有的Entity，
     * 但是spring boot 3.0 之后,这个方法不能正常的使用,无法动态添加Entity,
     * <p>
     * 所以这里改使用setManagedTypes()方法来实现
     */
    @PostConstruct
    public void initTables() {
        String[] rbacEntityPackages = lanJpaEntityManager.getRbacEntityPackages();

        // 临时创建一个factoryBean，用于初始化rbac相关的数据库表结构
        LocalContainerEntityManagerFactoryBean tempFactoryBean = buildFactoryBean(builder, rbacEntityPackages, new HibernateJpaVendorAdapter() {{
            setGenerateDdl(true);
            setShowSql(true);
        }});
        tempFactoryBean.destroy();
        // 合并所有Entity
        List<String> allEntityScanPackagesList = new ArrayList<>(Objects.requireNonNull(tempFactoryBean.getPersistenceUnitInfo()).getManagedClassNames());
        allEntityScanPackagesList.addAll(Objects.requireNonNull(localContainerEntityManagerFactoryBean.getPersistenceUnitInfo()).getManagedClassNames());
        localContainerEntityManagerFactoryBean.setManagedTypes(PersistenceManagedTypes.of(allEntityScanPackagesList.toArray(new String[0])));
        // 更新EntityManagerFactory
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
    }

    /**
     * 构建一个临时的LocalContainerEntityManagerFactoryBean,用于初始化rbac相关的数据库表结构 使用
     *
     * @param builder               EntityManagerFactoryBuilder
     * @param allEntityScanPackages 所有的Entity包路径
     * @param jpaVendorAdapter      JpaVendorAdapter
     * @return LocalContainerEntityManagerFactoryBean
     */
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
