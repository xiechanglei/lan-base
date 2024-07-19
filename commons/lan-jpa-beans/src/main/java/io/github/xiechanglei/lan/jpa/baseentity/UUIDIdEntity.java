package io.github.xiechanglei.lan.jpa.baseentity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


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
    @GenericGenerator(name = "uuid", type = io.github.xiechanglei.lan.jpa.generator.UUIDGenerator.class)
    @Column(length = 32)
    private String id;
}
