package io.github.xiechanglei.lan.rbac.custorm;

import io.github.xiechanglei.lan.rbac.entity.SysUserAuth;

/**
 * 用户自定义token信息,可以根据自己的业务需求来定义，实现这个接口即可
 */
public interface TokenInfo {

    String getUserId();

    //序列号,主要用来踢出用户用的，每次修改用户的密码之后，都会更新这个序列号，然后之前登陆的用户就会被踢出
    Short getSerialNumber();

    void init(SysUserAuth user);

}

