package io.github.xiechanglei.lan.base.rbac;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan
@EnableJpaRepositories
@EnableJpaAuditing
@ComponentScan("io.github.xiechanglei.lan.base.rbac")
public class LanBaseRbacDynamicConfig {
}
