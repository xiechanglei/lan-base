package io.github.xiechanglei.lan.rbac.custorm;

/**
 * 加密规则实现类,可以自定义加密规则，
 * 实现类使用spi的形式加载，@AutoService(RbacEncodeStrategy.class)
 *
 * @see io.github.xiechanglei.lan.rbac.provide.RbacEncodeProcessor
 */
public interface RbacEncodeStrategy {
    String encode(String value);
}
