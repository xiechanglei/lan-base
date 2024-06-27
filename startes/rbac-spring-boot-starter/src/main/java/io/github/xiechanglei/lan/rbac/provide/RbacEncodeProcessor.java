package io.github.xiechanglei.lan.rbac.provide;

import io.github.xiechanglei.lan.digest.md5.Md5Helper;
import io.github.xiechanglei.lan.rbac.custorm.RbacEncodeStrategy;

import java.util.ServiceLoader;

/**
 * 自定义密码加密策略，使用SPI机制，实现CustomRbacEncodeStrategy接口即可
 */
public class RbacEncodeProcessor {
    private static RbacEncodeStrategy rbacEncodeStrategy;
    static{
        ServiceLoader<RbacEncodeStrategy> load = ServiceLoader.load(RbacEncodeStrategy.class);
        for (RbacEncodeStrategy encodeStrategy : load) {
            rbacEncodeStrategy = encodeStrategy;
            break;
        }
        if(rbacEncodeStrategy == null){
            rbacEncodeStrategy = Md5Helper::encode;
        }
    }

    public static String encode(String pass) {
        return rbacEncodeStrategy.encode(pass);
    }

}


