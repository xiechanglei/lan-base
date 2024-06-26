package io.github.xiechanglei.lan.base.jpa.dsl;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class JpaQueryHelper {
    /**
     * 分页查询
     *
     * @param page 分页页码，第一页是0
     * @param size 分页的大小
     */
    public static <T> Page<T> fetchPage(JPAQuery<T> query, Integer page, Integer size) {
        List<T> result = query.limit(size).offset((long) page * size).fetch();
        return new PageImpl<>(result, PageRequest.of(page, size), query.fetchCount());
    }
}
