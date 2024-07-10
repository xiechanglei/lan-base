package io.github.xiechanglei.lan.test;

import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class TestUser extends SysUserAuth {
    private String address;

    @Override
    public void configAdmin() {
        this.setNickName("xiechanglei");
        this.setAddress("china");
    }
}
