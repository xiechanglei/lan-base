package io.github.xiechanglei.lan.rbac.service;

import com.blazebit.persistence.PagedList;
import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.jpa.dsl.BlazeJPAQueryProvider;
import io.github.xiechanglei.lan.rbac.entity.log.SysLog;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static io.github.xiechanglei.lan.rbac.entity.log.QSysLog.sysLog;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.rbac", name = "enable-log", havingValue = "true", matchIfMissing = true)
public class LanSysLogService {
    private final JPAQueryFactory jpaQueryFactory;
    private final BlazeJPAQueryProvider blazeJPAQueryProvider;


    /**
     * 分页查询日志
     *
     * @param pageRequest 分页参数pageNo，pageSize
     * @param ip          用户ip
     * @param title       日志标题
     * @param startTime   开始时间
     * @param endTime     结束时间
     */
    public Page<SysLog> searchLog(PageRequest pageRequest, String ip, String title, Date startTime, Date endTime) {
        BlazeJPAQuery<SysLog> query = blazeJPAQueryProvider.build().select(sysLog).from(sysLog).orderBy(sysLog.logTime.desc());

        if (StringUtils.hasText(ip)) {
            query.where(sysLog.logAddress.eq(ip));
        }
        if (StringUtils.hasText(title)) {
            query.where(sysLog.logTitle.like("%" + title + "%"));
        }
        if (startTime != null || endTime != null) {
            query.where(sysLog.logTime.between(startTime, endTime));
        }
        PagedList<SysLog> sysLogs = query.fetchPage((int) pageRequest.getOffset(), pageRequest.getPageSize());
        return new PageImpl<>(sysLogs, pageRequest, sysLogs.getTotalSize());
    }
}