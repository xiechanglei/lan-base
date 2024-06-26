package io.github.xiechanglei.lan.base.rbac.provide;

import io.github.xiechanglei.lan.base.utils.md5.Md5Helper;
import io.github.xiechanglei.lan.base.rbac.custorm.RbacEncodeStrategy;

import java.util.ServiceLoader;

/**
 * 自定义密码加密策略，使用SPI机制，实现CustomRbacEncodeStrategy接口即可
 */
public class RbacEncodeProcessor {

    private static RbacEncodeStrategy customRbacEncodeStrategy;

    static {
        ServiceLoader<RbacEncodeStrategy> serviceLoader = ServiceLoader.load(RbacEncodeStrategy.class);
        for (RbacEncodeStrategy strategy : serviceLoader) {
            customRbacEncodeStrategy = strategy;
        }
        if (customRbacEncodeStrategy == null) {
            customRbacEncodeStrategy = Md5Helper::encode;
        }
    }

    public static String encode(String pass) {
        return customRbacEncodeStrategy.encode(pass);
    }

}


