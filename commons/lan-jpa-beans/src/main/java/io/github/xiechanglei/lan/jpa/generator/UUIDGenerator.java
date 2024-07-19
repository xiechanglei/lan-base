package io.github.xiechanglei.lan.jpa.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

/**
 * TODO uuid生成器，看看Deprecated问题在哪里，修复一下
 */
public class UUIDGenerator extends org.hibernate.id.UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        return super.generate(session, o).toString().replace("-", "");
    }
}