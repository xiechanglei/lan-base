package io.github.xiechanglei.lan.base.rbac.token;

import io.github.xiechanglei.lan.base.beans.exception.BusinessException;
import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;

import java.util.ServiceLoader;

public class TokenInfoManager {

    private static TokenInfo tokenInfo;

    static {
        ServiceLoader<TokenInfo> serviceLoader = ServiceLoader.load(TokenInfo.class);
        for (TokenInfo token : serviceLoader) {
            tokenInfo = token;
        }
        if (tokenInfo == null) {
            tokenInfo = new DefaultTokenInfo();
        }
    }

    public static Class<? extends TokenInfo> getTokenInfoClass() {
        return tokenInfo.getClass();
    }

    public static TokenInfo buildTokenInfo(SysUserAuth user) {
        try {
            TokenInfo token = getTokenInfoClass().newInstance();
            token.init(user);
            return token;
        } catch (Exception e) {
            throw BusinessException.of(-1, "自定义token无空构造函数");
        }

    }
}