package io.github.xiechanglei.lan.base.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("io.github.xiechanglei.lan.base.web")
@PropertySource("classpath:lan.base.web.properties")
public class LanBaseWebAutoConfiguration {

}