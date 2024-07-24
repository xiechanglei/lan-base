package io.github.xiechanglei.lan.eureka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "lan.eureka")
public class LanEurekaProperties {
    /**
     * 是否使用默认的解码器,默认为true
     */
    private boolean useDefaultDecoder = true;
}
