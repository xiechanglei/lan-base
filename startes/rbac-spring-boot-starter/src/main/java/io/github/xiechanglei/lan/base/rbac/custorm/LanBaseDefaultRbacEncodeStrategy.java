package io.github.xiechanglei.lan.base.rbac.custorm;

import io.github.xiechanglei.lan.base.digest.md5.Md5Helper;

public class LanBaseDefaultRbacEncodeStrategy implements RbacEncodeStrategy {

    @Override
    public String encode(String value) {
        return Md5Helper.encode(value);
    }
}
