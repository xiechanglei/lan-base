package io.github.xiechanglei.lan.base.rbac.token;

import io.github.xiechanglei.lan.base.utils.aes.AESHelper;
import io.github.xiechanglei.lan.base.rbac.custorm.TokenInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenHandler {
    private static final String key = "lanbase-rbac";
    private static final String iv = "lanbase-rbac-auth";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String encode(TokenInfo token) {
        try {
            //objectMapper.writeValueAsString将对象转换为json字符串
            return AESHelper.encode(objectMapper.writeValueAsString(token), key, iv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static TokenInfo decode(String token) {
        try {
            return objectMapper.readValue(AESHelper.decode(token, key, iv), TokenInfoManager.getTokenInfoClass());
        } catch (Exception e) {
            return null;
        }
    }
}
