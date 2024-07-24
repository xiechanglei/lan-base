package io.github.xiechanglei.lan.eureka.decoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.beans.message.WebResult;
import io.github.xiechanglei.lan.eureka.decoder.converter.Converter;
import io.github.xiechanglei.lan.eureka.decoder.converter.PageConverter;
import io.github.xiechanglei.lan.json.JsonHelper;
import io.github.xiechanglei.lan.json.TextJsonContainer;
import io.github.xiechanglei.lan.lang.reflect.SyntheticParameterizedType;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * 自定义webResult 结构的解码器
 */
@Component("feignDecoder")
@ConditionalOnProperty(prefix = "lan.eureka", name = "use-default-decoder", havingValue = "true", matchIfMissing = true)
@Log4j2
public class LanEurekaWebResultDecoder implements Decoder {
    // 对于一些无法直接进行解析的类型，需要通过定义converter进行解析
    private static final List<Converter> converters = List.of(new PageConverter());

    /**
     * Decodes an HTTP response into a value of some type {@code T}. Whether this is
     * @param response the response to decode
     * @param type {@link java.lang.reflect.Method#getGenericReturnType() generic return type} of the
     *        method corresponding to this {@code response}.
     * @return An instance of type {@code T}.
     * @throws BusinessException if there was a non-recoverable problem decoding the response.
     */
    @Override
    public Object decode(Response response, Type type) throws BusinessException {
        try {
            checkResponse(response);
            if (response.body() == null) {
                return null;
            }
            String body = new String(Util.toByteArray(response.body().asInputStream()), StandardCharsets.UTF_8);
            log.info("feign response body:{}", body);

            Optional<Converter> optionalConverter = converters.stream().filter(converter -> converter.support(type)).findFirst();
            if (optionalConverter.isPresent()) {
                return decodeWithConverter(body, optionalConverter.get(), type);
            } else {
                return decodeWithNormalType(body, type);
            }
        } catch (Exception e) {
            throw e instanceof BusinessException ? (BusinessException) e : BusinessException.of("微服务结果解析失败");
        }
    }

    /**
     * 使用converter进行解析
     *
     * @param body      JSON 内容
     * @param converter 转换器
     * @param type      类型
     * @return 解析结果
     */
    public Object decodeWithConverter(String body, Converter converter, Type type) {
        TextJsonContainer jsonContainer = new TextJsonContainer(body);
        boolean success = jsonContainer.readJson("$.success", Boolean.class);
        if (!success) {
            String message = jsonContainer.readJson("$.msg", String.class);
            int code = jsonContainer.readJson("$.code", Integer.class);
            throw BusinessException.of(code, message);
        }
        return converter.convert(jsonContainer, type);
    }

    /**
     * 使用普通类型进行解析
     *
     * @param body JSON 内容
     * @param type 类型
     * @return 解析结果
     * @throws JsonProcessingException JSON 解析异常
     */
    public Object decodeWithNormalType(String body, Type type) throws JsonProcessingException {
        SyntheticParameterizedType composeType = SyntheticParameterizedType.of(WebResult.class, type);
        WebResult<?> o = JsonHelper.fromJson(body, composeType);
        if (!o.isSuccess()) {
            throw BusinessException.of(o.getCode(), o.getMsg());
        }
        return o.getData();
    }


    private void checkResponse(Response response) {
        if (response.status() != 200) {
            throw BusinessException.of("微服务调用失败：" + response.status());
        }
    }
}