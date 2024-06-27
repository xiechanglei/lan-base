package io.github.xiechanglei.lan.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManager;

/**
 * jpa的一些常规配置，比如数据库连接池的大小设置等等
 */
@Configuration
public class LanBaseJpaConfigProperties {
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}