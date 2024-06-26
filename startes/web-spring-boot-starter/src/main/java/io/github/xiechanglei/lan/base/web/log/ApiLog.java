package io.github.xiechanglei.lan.base.web.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiLog {
    // 接口名称
    String value() default "";

    // 标记需要被打印的参数
    String[] params() default {};

}
