package io.github.xiechanglei.lan.rbac.swit;

import io.github.xiechanglei.lan.rbac.custorm.LanApiLogDataStoreHandler;
import io.github.xiechanglei.lan.rbac.repo.LanSysLogRepository;
import io.github.xiechanglei.lan.web.log.LanApiLogHandler;
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
@EntityScan("io.github.xiechanglei.lan.rbac")
@EnableJpaRepositories("io.github.xiechanglei.lan.rbac")
@ComponentScan("io.github.xiechanglei.lan.rbac")
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanRbacDynamicConfig {
    @Resource
    private LanSysLogRepository lanBaseSysLogRepository;

    @Bean
    public LanApiLogHandler lanBaseApiLogHandler() {
        return new LanApiLogDataStoreHandler(lanBaseSysLogRepository);
    }
}
