package io.github.xiechanglei.lan.lang.net;

import io.github.xiechanglei.lan.lang.net.vo.LocalIpAddressInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * IP工具类
 */
public class IpHandler {
    /**
     * 判断指定IP是否可达
     *
     * @param host IP地址
     * @return 是否可达
     */
    public static boolean isReachable(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.isReachable(3000);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 获取本机的IP地址信息列表
     */
    public static List<LocalIpAddressInfo> getLocalIpAddressInfo() throws SocketException, UnknownHostException {
        List<LocalIpAddressInfo> list = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            LocalIpAddressInfo localIpAddressInfo = new LocalIpAddressInfo();
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            int currentIndex = 0;
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                    localIpAddressInfo.setIp(inetAddress.getHostAddress());
                    localIpAddressInfo.setName(networkInterface.getDisplayName());
                    localIpAddressInfo.setMtu(networkInterface.getMTU());
                    byte[] hardwareAddress = networkInterface.getHardwareAddress();
                    if (hardwareAddress != null) {
                        String[] hexadecimal = new String[hardwareAddress.length];
                        for (int i = 0; i < hardwareAddress.length; i++) {
                            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
                        }
                        localIpAddressInfo.setMac(String.join(":", hexadecimal));
                    }
                    localIpAddressInfo.setNetmask(formatNetMask(networkInterface.getInterfaceAddresses().get(currentIndex).getNetworkPrefixLength()));
                    localIpAddressInfo.setBroadcast(networkInterface.getInterfaceAddresses().get(currentIndex).getBroadcast().getHostAddress());
                    list.add(localIpAddressInfo);
                }
                currentIndex++;
            }
        }

        return list;
    }

    /**
     * 转换子网掩码 int => string
     *
     * @param prefixLength 子网掩码长度
     */
    public static String formatNetMask(int prefixLength) throws UnknownHostException {
        int mask = -1 << (32 - prefixLength);
        byte[] bytes = new byte[]{
                (byte) (mask >>> 24),
                (byte) (mask >> 16 & 0xFF),
                (byte) (mask >> 8 & 0xFF),
                (byte) (mask & 0xFF)
        };
        InetAddress netAddr = InetAddress.getByAddress(bytes);
        return netAddr.getHostAddress();
    }

}
