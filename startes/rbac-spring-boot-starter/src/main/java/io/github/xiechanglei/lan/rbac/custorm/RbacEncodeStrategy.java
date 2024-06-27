package io.github.xiechanglei.lan.rbac.custorm;

/**
 * 加密规则实现类
 *
 * @see io.github.xiechanglei.lan.rbac.provide.RbacEncodeProcessor
 */
public interface RbacEncodeStrategy {
    String encode(String value);
}
