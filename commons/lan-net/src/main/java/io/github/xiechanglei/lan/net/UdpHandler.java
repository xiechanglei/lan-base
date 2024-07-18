package io.github.xiechanglei.lan.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * UDP工具类
 */
public class UdpHandler {
    /**
     * 发送数据到指定IP和端口
     *
     * @param ip   ip地址
     * @param port 端口
     * @param data 数据
     */
    public static byte[] sendData(String ip, int port, byte[] data) throws IOException {
        return sendData(ip, port, data, 0, 0);
    }

    /**
     * 发送数据到指定IP和端口
     *
     * @param ip                       ip地址
     * @param port                     端口
     * @param data                     数据
     * @param maxReceivedPackageLength 最大接收包长度，如果为0则不接收返回数据
     */
    public static byte[] sendData(String ip, int port, byte[] data, int maxReceivedPackageLength, int receivedTimeout) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(receivedTimeout);
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            socket.send(packet);
            if (maxReceivedPackageLength > 0) {
                byte[] bytes = new byte[maxReceivedPackageLength];
                packet = new DatagramPacket(bytes, bytes.length);
                socket.receive(packet);
                return Arrays.copyOf(bytes, packet.getLength());
            }
            return null;
        }
    }

    /**
     * 扫描网段内所有打开了指定端口的主机IP地址，需要指定发送数据，并且有返回数据的才能判断端口是否打开
     *
     * @param ipBlock 网段，如 192.168.1
     * @param port    端口
     * @return 打开了指定端口的主机IP地址列表
     */
    public static List<String> scanPort(String ipBlock, int port, byte[] message, int receivedTimeout) throws InterruptedException {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(254);
        for (int i = 1; i <= 254; i++) { // 0和255分别表示网络号和广播地址
            String ip = ipBlock + "." + i;
            new Thread(() -> {
                try {
                    byte[] bytes = sendData(ip, port, message, 1024, receivedTimeout);
                    if (bytes != null) {
                        list.add(ip);
                    }
                } catch (IOException ignored) {
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        return list;
    }

}

