package io.github.xiechanglei.lan.rbac.entity.user;


import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

/**
 * 内置的用户表，业务系统可以自己定义用户表，但是必须继承SysUserAuth
 */
@Entity
@Table(name = "sys_user", indexes = {
        @Index(name = "idx_user_name", columnList = "user_name")})
public final class SysUser extends SysUserAuth {
    @Override
    public void configAdmin() {
        // do nothing
    }
}
