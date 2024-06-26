package io.github.xiechanglei.lan.base.jpa.baseentity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * 扩展了雪花算法生成ID和时间字段的实体基类
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class SnowFlakeIdAndTimeFieldEntity {
    /**
     * 雪花算法生成ID
     */
    @Id
    @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "io.github.xiechanglei.lan.base.jpa.generator.SnowFlakeIdGenerator")
    @GeneratedValue(generator = "snowFlakeIdGenerator")
    @Column(length = 40)
    public String id;


    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;
}