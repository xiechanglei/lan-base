package io.github.xiechanglei.lan.jpa.baseentity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @GenericGenerator(name = "snowFlakeIdGenerator", type = io.github.xiechanglei.lan.jpa.generator.SnowFlakeIdGenerator.class)
    @GeneratedValue(generator = "snowFlakeIdGenerator")
    @Column(length = 40)
    public String id;
}