package io.github.xiechanglei.lan.base.rbac.swit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EntityScan("io.github.xiechanglei.lan.base.rbac")
@EnableJpaRepositories("io.github.xiechanglei.lan.base.rbac")
@ComponentScan("io.github.xiechanglei.lan.base.rbac")
@ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanBaseRbacDynamicConfig {

}
