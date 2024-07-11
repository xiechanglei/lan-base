package io.github.xiechanglei.lan.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("io.github.xiechanglei.lan.web")
@PropertySource("classpath:lan.web.properties")
public class LanWebAutoConfiguration {}