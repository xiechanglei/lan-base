package io.github.xiechanglei.lan.base.rbac.custorm;


import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;


/**
 * 用户自定义token信息,可以根据自己的业务需求来定义，
 * 定义的时候需要使用spi的形式进行配置，如：
 * <code>
 *
 * @ AutoService(TokenInfo.class) public MyTokenInfo implements TokenInfo{
 * //实现方法
 * xxxx
 * }
 * </code>
 * 也可以继承DefaultTokenInfo，然后重写init方法,增加额外字段
 */
public interface TokenInfo {

    String getUserId();

    //序列号,主要用来踢出用户用的，每次修改用户的密码之后，都会更新这个序列号，然后之前登陆的用户就会被踢出
    Short getSerialNumber();

    void init(SysUserAuth user);

}

