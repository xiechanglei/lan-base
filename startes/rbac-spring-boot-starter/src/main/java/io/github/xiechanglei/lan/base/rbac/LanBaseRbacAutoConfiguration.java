package io.github.xiechanglei.lan.base.rbac;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

public class LanBaseRbacAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "lan.base.rbac", name = "enable", havingValue = "true", matchIfMissing = true)
    public LanBaseRbacDynamicConfig lanBaseRbacDynamicConfig() {
        return new LanBaseRbacDynamicConfig();
    }
}