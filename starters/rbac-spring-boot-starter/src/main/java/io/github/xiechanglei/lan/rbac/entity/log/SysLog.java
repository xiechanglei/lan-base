package io.github.xiechanglei.lan.rbac.entity.log;

import io.github.xiechanglei.lan.jpa.baseentity.SnowFlakeIdEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "sys_log")
@EntityListeners(AuditingEntityListener.class)
public class SysLog extends SnowFlakeIdEntity {
    /**
     * 用户ID
     */
    @Column(name = "user_id", length = 40)
    private String userId;

    /**
     * 日志标题
     */
    @Column(name = "log_title", length = 100)
    private String logTitle;

    /**
     * 请求路径
     */
    @Column(name = "log_path", length = 100)
    private String logPath;

    /**
     * 请求IP地址
     */
    @Column(name = "log_address", length = 100)
    private String logAddress;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(nullable = false, name = "log_time", columnDefinition = "datetime comment '创建时间'")
    private Date logTime;

    /**
     * 日志类型
     */
    @Column(name = "log_params", length = 200, nullable = false)
    private String params;

    public SysLog() {
    }
}
