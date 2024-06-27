package io.github.xiechanglei.lan.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("io.github.xiechanglei.lan.base.jpa")
@PropertySource("classpath:lan.base.jpa.properties")
public class LanBaseJpaAutoConfiguration {
}