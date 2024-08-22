package io.github.xiechanglei.lan.nginx.config;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * 模拟Nginx配置类，目前项目设计一个Nginx配置类只能启动一个端口服务，即一个nginx里面的server
 */
@Builder
@Getter
public class NginxConfig {
    // worker进程数
    private int workerGroup;

    // 服务端口
    private int port;

    // location配置
    @Builder.Default
    private List<NginxLocation> locations = Collections.emptyList();


    //gzip todo
    @Builder.Default
    private boolean gzip = true;

    /**
     * 对配置进行初始化
     */
    public NginxConfig init() {
        // 对location进行排序，Priority 约小越优先
        locations.sort(Comparator.comparingInt(location -> location.getRule().getPriority()));
        // 校验配置的合法性
        locations.forEach(NginxLocation::checkConfig);
        return this;
    }


    /**
     * 匹配location
     */
    public NginxLocation matchLocation(String uri) {
        NginxLocation matchLocation = null;
        Optional<NginxLocation> findOption = locations.stream().filter(location -> location.match(uri)).findFirst();
        if (findOption.isPresent()) {
            matchLocation = findOption.get();
        }
        return matchLocation;
    }

}
