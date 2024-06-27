package io.github.xiechanglei.lan.rbac.entity;


import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 内置的用户表，业务系统可以自己定义用户表，但是必须继承SysUserAuth

 */
@Entity
@Table(name = "sys_user", indexes = {
        @Index(name = "idx_user_name", columnList = "user_name")})
@SuppressWarnings("all")
public final class SysUser extends SysUserAuth {
}
