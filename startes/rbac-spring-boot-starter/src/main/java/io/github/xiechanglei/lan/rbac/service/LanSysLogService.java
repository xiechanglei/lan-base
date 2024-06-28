package io.github.xiechanglei.lan.rbac.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.xiechanglei.lan.jpa.dsl.JpaQueryHelper;
import io.github.xiechanglei.lan.rbac.entity.SysLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static io.github.xiechanglei.lan.rbac.entity.QSysLog.sysLog;

@Service
@RequiredArgsConstructor
public class LanSysLogService {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 分页查询日志
     * @param pageRequest 分页参数pageNo，pageSize
     * @param ip 用户ip
     * @param title 日志标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public Page<SysLog> searchLog(PageRequest pageRequest, String ip, String title, Date startTime, Date endTime) {
        JPAQuery<SysLog> query = jpaQueryFactory.selectFrom(sysLog).orderBy(sysLog.logTime.desc());
        if (StringUtils.hasText(ip)) {
            query.where(sysLog.logAddress.eq(ip));
        }
        if (StringUtils.hasText(title)) {
            query.where(sysLog.logTitle.like("%" + title + "%"));
        }
        if (startTime != null || endTime != null) {
            query.where(sysLog.logTime.between(startTime, endTime));
        }
        return JpaQueryHelper.fetchPage(query, pageRequest.getPageNumber(), pageRequest.getPageSize());
    }
}