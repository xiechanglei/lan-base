package io.github.xiechanglei.lan.eureka.formatter;

import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LanEurekaFeignDateFormatRegister implements FeignFormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addConverter(Date.class, String.class, new Date2StringConverter());
    }

    private static class Date2StringConverter implements Converter<Date, String> {
        @Override
        public String convert(Date source) {
            return String.valueOf(source.getTime());
        }
    }
}
