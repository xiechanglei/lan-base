package io.github.xiechanglei.lan.rbac.token;

import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.rbac.entity.base.SysUserAuth;
import lombok.Getter;

import java.util.ServiceLoader;

public class TokenInfoManager {
    @Getter
    private static Class<? extends TokenInfo> tokenInfoClass;

    static {
        ServiceLoader<TokenInfo> load = ServiceLoader.load(TokenInfo.class);
        for (TokenInfo tokenInfo : load) {
            tokenInfoClass = tokenInfo.getClass();
        }
        if(tokenInfoClass == null) {
            tokenInfoClass = LanDefaultTokenInfo.class;
        }
    }

    public static TokenInfo buildTokenInfo(SysUserAuth user) {
        try {
            TokenInfo token = tokenInfoClass.newInstance();
            token.init(user);
            return token;
        } catch (Exception e) {
            throw BusinessException.of(-1, "自定义token无空构造函数");
        }
    }
}