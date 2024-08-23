package io.github.xiechanglei.lan.nginx.config;

import io.github.xiechanglei.lan.nginx.DefaultConfig;
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

    @Builder.Default
    private String serverName = DefaultConfig.DEFAULT_SERVER_NAME;

    /**
     * worker进程数，按照netty的设计，默认为cpu核数的2倍
     */
    private int workerGroup;

    /**
     * 服务的端口
     */
    private int port;

    /**
     * location配置，配置多个location
     */
    @Builder.Default
    private List<NginxLocation> locations = Collections.emptyList();


    /**
     * 全局gzip
     */
    private Boolean gzip;

    /**
     * 全局charset
     */

    private String charset;

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
     * 匹配uri对应的location
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
