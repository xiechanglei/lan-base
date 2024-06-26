package io.github.xiechanglei.lan.base.jpa.baseentity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * 使用UUID生成ID的实体基类，用在数据量比较小的表上
 */
@Getter
@Setter
@MappedSuperclass
public class UUIDIdEntity {
    /*
     * UUID生成ID
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "io.github.xiechanglei.lan.base.jpa.generator.UUIDGenerator")
    @Column(length = 32)
    private String id;
}
