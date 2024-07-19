package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TestUser extends SysUserAuth {
    private String address;

    @Override
    public void configAdmin() {
        this.setNickName("xiechanglei");
        this.setAddress("china");
    }
}
