package io.github.xiechanglei.lan.base.jpa.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * JPA 雪花算法ID生成器
 */
public class SnowFlakeIdGenerator implements IdentifierGenerator {

    public static final SnowFlake snowFlake = new SnowFlake(1, 1, 20);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        return snowFlake.nextId();
    }

}