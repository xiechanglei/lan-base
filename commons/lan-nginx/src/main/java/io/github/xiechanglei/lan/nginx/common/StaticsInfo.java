package io.github.xiechanglei.lan.nginx.common;

import io.github.xiechanglei.lan.lang.date.DateHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务统计信息
 */
@Getter
@Log4j2
@RequiredArgsConstructor
public class StaticsInfo {
    /**
     * 服务端口
     */
    private final int port;
    /**
     * 服务启动时间
     */
    private final String startTime = DateHelper.format(new Date());

    /**
     * 历史连接总数
     */
    private final AtomicLong totalConnection = new AtomicLong(0);

    /**
     * 当前连接数
     */
    private final AtomicLong currentConnection = new AtomicLong(0);

    /**
     * 历史请求总数
     */
    private final AtomicLong totalRequest = new AtomicLong(0);

    /**
     * 当前请求总数
     */
    private final AtomicLong currentRequest = new AtomicLong(0);

    /**
     * 打印统计信息
     */
    public void print() {
        log.info("服务端口:{},启动时间:{},历史连接总数:{},当前连接数:{},历史请求总数:{},当前请求总数:{}",
                port, startTime, totalConnection.get(), currentConnection.get(), totalRequest.get(), currentRequest.get());
    }

    public void addCurrentRequest() {
        currentRequest.incrementAndGet();
        totalRequest.incrementAndGet();
    }

    public void subCurrentRequest() {
        currentRequest.decrementAndGet();
    }

    public void addCurrentConnection() {
        currentConnection.incrementAndGet();
        totalConnection.incrementAndGet();
    }


    public void subCurrentConnection() {
        currentConnection.decrementAndGet();
    }

}
