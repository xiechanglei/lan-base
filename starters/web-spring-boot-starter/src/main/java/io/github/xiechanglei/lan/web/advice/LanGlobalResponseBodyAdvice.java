package io.github.xiechanglei.lan.web.advice;

import io.github.xiechanglei.lan.beans.message.WebResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 * 目前，前后端分离是主流的开发模式，在前后端分离的开发模式中，前端通过调用后端提供的接口来获取数据，后端返回的数据格式一般为json格式，
 * 为了方便前端处理数据，一般会对返回的数据进行封装，这样前端编写请求结果的拦截操作，从而进行全局处理，
 * 例如，区分操作成功和操作失败之后可以统一进行提示，而不需要每个接口都进行处理。
 * 常规开发中，我们会在每个接口中进行返回结果的封装，这样会增加代码量，而且不利于维护，
 * <p>
 * 所以这里设计，在接口方法中，我们只关注返回的数据，而不关注返回的数据格式，数据格式交给框架由全局处理，
 * 使接口的逻辑更加纯粹，方便进行单元测试与维护。
 * <p>
 * 另外我们需要考虑到一些失败的情况，比如对用户进行修改的时候，用户不存在，这个时候我们需要返回一个错误的提示，
 * 正常的逻辑中，我们会抛出一个异常，而ResponseBodyAdvice是无法处理异常的，所以我们需要联合使用全局异常处理来使用.
 */
@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "lan.web.advice.response", name = "enable", havingValue = "true", matchIfMissing = true)
public class LanGlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @PostConstruct
    public void init() {
        log.info("开启全局返回结果封装 (lan.web.advice.response.enable=true)...");
    }

    private final ObjectMapper objectMapper;

    /**
     * 判断是否支持封装返回结果，当标注了{@link DisableResponseAdvice}注解的时候，不进行封装
     */
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return returnType.getDeclaringClass().getAnnotation(DisableResponseAdvice.class) == null &&
                Objects.requireNonNull(returnType.getMethod()).getAnnotation(DisableResponseAdvice.class) == null;
    }

    /**
     * 封装全局返回结果为WebResult的success模式
     */
    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType,
                                  @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NonNull ServerHttpRequest request,
                                  @NonNull ServerHttpResponse response) {

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        WebResult<?> result;
        if (body instanceof WebResult) {
            result = (WebResult<?>) body;
        } else {
            result = WebResult.success(body);
        }

//       String 类型的body不进行封装,spring boot 框架的问题，
        if (body instanceof String) {
            try {
                response.getBody().write(objectMapper.writeValueAsString(result).getBytes());
                return null;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}