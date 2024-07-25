package io.github.xiechanglei.lan.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cat {
    @Id
    private String id;
    private String name;
    private int age;
}
