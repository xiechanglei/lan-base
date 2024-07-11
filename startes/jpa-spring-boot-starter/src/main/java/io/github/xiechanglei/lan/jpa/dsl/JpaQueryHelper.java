package io.github.xiechanglei.lan.jpa.dsl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class JpaQueryHelper {
    /**
     * 分页查询
     */
    public static <T> Page<T> fetchPage(JPAQuery<T> query, PageRequest pageRequest) {
        List<T> result = query.limit(pageRequest.getPageSize()).offset(pageRequest.getOffset()).fetch();
        return new PageImpl<>(result, pageRequest, query.fetchCount());
    }
}
