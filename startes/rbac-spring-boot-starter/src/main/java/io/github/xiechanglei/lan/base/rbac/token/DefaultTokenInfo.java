package io.github.xiechanglei.lan.base.rbac.token;

import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultTokenInfo implements TokenInfo {
    private String userId;
    //序列号,主要用来踢出用户用的，每次修改用户的密码之后，都会更新这个序列号，然后之前登陆的用户就会被踢出
    private Short serialNumber;

    @Override
    public void init(SysUserAuth user) {
        this.userId = user.getId();
        this.serialNumber = user.getUserSerial();
    }
}

