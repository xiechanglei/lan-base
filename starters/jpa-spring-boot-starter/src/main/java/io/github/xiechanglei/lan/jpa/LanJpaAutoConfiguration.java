package io.github.xiechanglei.lan.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("io.github.xiechanglei.lan.jpa")
@PropertySource("classpath:lan.jpa.properties")
public class LanJpaAutoConfiguration {
}