package io.github.xiechanglei.lan.base.jpa.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

public class UUIDGenerator extends org.hibernate.id.UUIDGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o) throws HibernateException {
        return super.generate(session, o).toString().replace("-", "");
    }
}