package io.github.xiechanglei.lan.eureka;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("io.github.xiechanglei.lan.eureka")
@PropertySource("classpath:lan.eureka.properties")
public class LanEurekaAutoConfiguration {
}
