package io.github.xiechanglei.lan.eureka.decoder.converter;

import io.github.xiechanglei.lan.json.JsonContainer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * spring data 中的Page对象转换器
 */
public class PageConverter implements Converter {
    @Override
    public Object convert(JsonContainer jsonContainer, Type type) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

        // 获取元素
        List<?> elements;
        if (actualTypeArguments.length == 1) {
            Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
            elements = jsonContainer.readList("$.data.content", actualTypeArgument);
        } else {
            elements = jsonContainer.readList("$.data.content", Object.class);
        }

        // 获取分页信息
        long totalElements = jsonContainer.readJson("$.data.totalElements", Long.class);
        int size = jsonContainer.readJson("$.data.size", Integer.class);
        int number = jsonContainer.readJson("$.data.number", Integer.class);
        PageRequest page = PageRequest.of(number, size);
        try {
            jsonContainer.readJson("$.data.pageable.pageNumber", Integer.class);
            List<SortTemp> sortTemps = jsonContainer.readList("$.data.sort", SortTemp.class);
            if (sortTemps != null && !sortTemps.isEmpty()) {
                SortTemp sortTemp = sortTemps.get(0);
                page = page.withSort(Sort.Direction.ASC.name().equalsIgnoreCase(sortTemp.direction) ? Sort.by(Sort.Order.asc(sortTemp.property)) : Sort.by(Sort.Order.desc(sortTemp.property)));
            }
            return new PageImpl<>(elements, page, totalElements);
        } catch (Exception e) {
            //如果报错了，说明是Pageable.unpaged()
            return new PageImpl<>(elements, Pageable.unpaged(), totalElements);
        }
    }

    @Getter
    @Setter
    public static class SortTemp {
        private String property;
        private String direction;
    }

    @Override
    public boolean support(Type type) {
        return type instanceof ParameterizedType parameterizedType && parameterizedType.getRawType() == Page.class;
    }
}
