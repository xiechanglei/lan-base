package io.github.xiechanglei.lan.rbac.custorm;

/**
 * 权限数据生成范围接口,配合注解AuthScope使用
 */
public interface DataAuthScope {
    Object buildScopeParam();
}
