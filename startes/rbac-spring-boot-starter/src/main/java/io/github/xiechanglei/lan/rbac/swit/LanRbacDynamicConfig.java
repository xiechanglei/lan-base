package io.github.xiechanglei.lan.rbac.swit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

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
public class LanRbacDynamicConfig {
}
