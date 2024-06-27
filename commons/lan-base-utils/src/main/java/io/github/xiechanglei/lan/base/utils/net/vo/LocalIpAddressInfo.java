package io.github.xiechanglei.lan.base.utils.net.vo;

import lombok.Data;

/**
 * ip地址信息
 */
@Data
public class LocalIpAddressInfo {
    private String ip; // ip地址
    private String netmask; // 子网掩码
    private String broadcast; // 广播地址
    private String mac; // mac地址
    private String name; // 网卡接口名称
    private Integer mtu; // 最大传输单元
}
