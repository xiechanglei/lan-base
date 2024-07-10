package io.github.xiechanglei.lan.rbac.custorm;

import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;

/**
 * 可以自己重写权限校验逻辑
 * 默认的权限拦截逻辑是从token中获取用户信息，然后去数据库中查询用户的权限信息进行校验，
 * 但是这种方式的问题是每次请求都需要查询数据库，可以自己重写权限校验逻辑，比如从redis中获取用户权限信息与角色权限信息，减少数据库查询次数
 * <p>
 * 建议检查以下几点：
 * 1.用户是否存在
 * 2.用户是否被禁用
 * 3.用户seq是否过期
 * 4.用户是否有权限访问当前接口
 * <p>
 * 默认实现：
 *
 * @see io.github.xiechanglei.lan.rbac.service.DefaultPermissionService
 * 使用spring bean的形式加载
 */
public interface PermissionService {

    /**
     * 校验权限,token种包含了userid,serialNumber等信息，
     * 业务系统可以自己实现如何通过token获取用户信息,并且校验权限
     *
     * @param tokenInfo   当前用户信息
     * @param permissions 当前访问接口需要的权限
     * @return 校验通过，返回当前用户信息
     * @throws BusinessException 没有权限抛出异常
     */
    SysUserAuth checkPermission(TokenInfo tokenInfo, String[] permissions) throws BusinessException;
}
