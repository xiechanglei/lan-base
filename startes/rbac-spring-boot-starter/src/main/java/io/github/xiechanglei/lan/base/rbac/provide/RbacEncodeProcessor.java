package io.github.xiechanglei.lan.base.rbac.provide;

import io.github.xiechanglei.lan.base.rbac.custorm.RbacEncodeStrategy;
import org.springframework.stereotype.Service;

/**
 * 自定义密码加密策略，使用SPI机制，实现CustomRbacEncodeStrategy接口即可
 */
@Service
public class RbacEncodeProcessor {

    private static RbacEncodeStrategy rbacEncodeStrategy;

    public RbacEncodeProcessor(RbacEncodeStrategy rbacEncodeStrategy) {
        RbacEncodeProcessor.rbacEncodeStrategy = rbacEncodeStrategy;
    }

    public static String encode(String pass) {
        return rbacEncodeStrategy.encode(pass);
    }

}


