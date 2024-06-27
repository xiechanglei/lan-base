package io.github.xiechanglei.lan.jpa.baseentity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 使用雪花算法生成ID的实体基类，用在数据量比较大的表上
 */
@Getter
@Setter
@MappedSuperclass
public abstract class SnowFlakeIdEntity {
    /**
     * 雪花算法生成ID
     */
    @Id
    @GenericGenerator(name = "snowFlakeIdGenerator", strategy = "io.github.xiechanglei.lan.jpa.generator.SnowFlakeIdGenerator")
    @GeneratedValue(generator = "snowFlakeIdGenerator")
    @Column(length = 40)
    public String id;
}