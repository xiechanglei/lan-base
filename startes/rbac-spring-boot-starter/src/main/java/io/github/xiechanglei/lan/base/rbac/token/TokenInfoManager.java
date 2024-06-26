package io.github.xiechanglei.lan.base.rbac.token;

import io.github.xiechanglei.lan.base.beans.exception.BusinessException;
import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import io.github.xiechanglei.lan.base.rbac.entity.SysUserAuth;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class TokenInfoManager {
    @Getter
    private static Class<? extends TokenInfo> tokenInfoClass;

    public TokenInfoManager(TokenInfo tokenInfo) {
        TokenInfoManager.tokenInfoClass = tokenInfo.getClass();
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