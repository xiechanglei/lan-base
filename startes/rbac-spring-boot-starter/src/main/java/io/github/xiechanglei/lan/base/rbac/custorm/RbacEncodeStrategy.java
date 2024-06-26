package io.github.xiechanglei.lan.base.rbac.custorm;

/**
 * 可以自己重写密码加密解密规则，默认为MD5加密
 *
 * @see io.github.xiechanglei.lan.base.rbac.provide.RbacEncodeProcessor
 */
public interface RbacEncodeStrategy {
    String encode(String value);
}
