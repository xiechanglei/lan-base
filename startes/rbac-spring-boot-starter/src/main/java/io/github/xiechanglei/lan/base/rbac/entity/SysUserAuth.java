package io.github.xiechanglei.lan.base.rbac.entity;

import io.github.xiechanglei.lan.jpa.baseentity.UUIDIdAndTimeFieldEntity;
import io.github.xiechanglei.lan.base.rbac.provide.RbacEncodeProcessor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 用户表
 */
@Getter
@Setter
@MappedSuperclass// 继承该类的子类不需要再写@Entity注解
@EntityListeners(AuditingEntityListener.class)//监听JPA实体持久化
public class SysUserAuth extends UUIDIdAndTimeFieldEntity {

    // 默认用户序列号
    public static Short DEFAULT_USER_SERIAL = 1;

    /**
     * 用户状态
     */
    public enum UserStatus {DISABLE, ENABLE}

    /**
     * 用户名称
     */
    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    /**
     * 用户密码
     */
    @JsonIgnore
    @Column(name = "user_password", length = 100, nullable = false)
    private String userPassword;

    /**
     * 用户状态
     */
    @Column(name = "user_status", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    /**
     * 昵称
     */
    @Column(name = "nick_name", length = 100)
    private String nickName;

    /**
     * 用户序列号，每次修改密码+1
     */
    @JsonIgnore
    @Column(name = "user_serial", length = 5)
    private Short userSerial;

    /**
     * 更新用户版本号
     */
    public void updateSerial() {
        if (userSerial == null) {
            userSerial = DEFAULT_USER_SERIAL;
        }
        userSerial++;
    }

    public SysUserAuth buildAdmin() {
        this.userStatus = UserStatus.ENABLE;
        this.userSerial = DEFAULT_USER_SERIAL;
        this.nickName = "admin";
        this.userName = "admin";
        this.userPassword = RbacEncodeProcessor.encode("123456");
        return this;
    }
}
