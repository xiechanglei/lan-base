package io.github.xiechanglei.lan.base.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 解析网页端传递的时间参数，将其转换成Date类型，简化controller中的参数接收处理逻辑
 * 1.格式为yyyy-MM-dd HH:mm:ss
 * 2.格式为yyyy-MM-dd
 * 3.时间戳
 */
@Component
public class LanBaseDateResolver implements HandlerMethodArgumentResolver {
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //gmt+8
    private static final ZoneId zoneId = ZoneId.of("GMT+8");

    /**
     * Whether the given method parameter is supported by this resolver.
     *
     * @param parameter the method parameter to check
     * @return true if this resolver supports the supplied parameter; false otherwise
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Date.class);
    }

    /**
     * Resolves a method parameter into an argument value from a given request.
     */
    @Override
    public Date resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String paramName = parameter.getParameterName();
        assert paramName != null;
        String dateStr = webRequest.getParameter(paramName);
        // If the dateStr is empty, return null
        if (!StringUtils.hasText(dateStr)) {
            return null;
        }
        if (dateStr.contains("-")) { // If the dateStr contains "-", it is a date format string

            if (dateStr.length() > 10) { // use LocalDateTime to parse the dateStr
                return Date.from(LocalDateTime.parse(dateStr, dateFormat).atZone(zoneId).toInstant());
            } else { // use LocalDate to parse the dateStr
                return Date.from(LocalDate.parse(dateStr, shortDateFormat).atStartOfDay(zoneId).toInstant());
            }

        } else { // If the dateStr is a timestamp
            return new Date(Long.parseLong(dateStr));
        }
    }
}
