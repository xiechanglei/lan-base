package io.github.xiechanglei.lan.lang.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TcpHandler {
    /**
     * 扫描网段内所有打开了指定端口的主机IP地址
     *
     * @param ipBlock 网段，如 192.168.1
     * @param port    端口
     * @return 打开了指定端口的主机IP地址列表
     */
    public static List<String> scanPort(String ipBlock, int port, int timeout) throws InterruptedException {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(254);
        for (int i = 1; i <= 254; i++) { // 0和255分别表示网络号和广播地址
            String ip = ipBlock + "." + i;
            new Thread(() -> {
                if (checkOpen(ip, port, timeout)) {
                    list.add(ip);
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        return list;
    }

    /**
     * TCP端口是否打开
     */
    public static boolean checkOpen(String ip, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }


}
