package io.github.xiechanglei.lan.base.rbac.swit;

import io.github.xiechanglei.lan.base.rbac.custorm.*;
import io.github.xiechanglei.lan.base.rbac.repo.LanBaseSysLogRepository;
import io.github.xiechanglei.lan.base.web.log.LanBaseApiLogHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

@Configuration
@EnableJpaAuditing
@EnableScheduling
@EntityScan("io.github.xiechanglei.lan.base.rbac")
@EnableJpaRepositories("io.github.xiechanglei.lan.base.rbac")
@ComponentScan("io.github.xiechanglei.lan.base.rbac")
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseRbacDynamicConfig {
    @Resource
    private LanBaseSysLogRepository lanBaseSysLogRepository;

    @Bean
    @ConditionalOnMissingBean(RbacEncodeStrategy.class)
    public RbacEncodeStrategy rbacEncodeStrategy() {
        return new LanBaseDefaultRbacEncodeStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(TokenInfo.class)
    public TokenInfo tokenInfo() {
        return new LanBaseDefaultTokenInfo();
    }

    @Bean
    public LanBaseApiLogHandler lanBaseApiLogHandler() {
        return new LanBaseApiLogDataBaseStoreHandler(lanBaseSysLogRepository);
    }
}
