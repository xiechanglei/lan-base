package io.github.xiechanglei.lan.eureka.decoder;

import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.json.TextJsonContainer;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * 捕获异常进行处理
 */
@Log4j2
@Service("feignErrorDecoder")
@ConditionalOnProperty(prefix = "lan.eureka", name = "use-default-decoder", havingValue = "true", matchIfMissing = true)
public class LanEurekaErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            if (503 == response.status()) {
                throw BusinessException.of("微服务未启动，请稍后再试");
            }
            if (response.body() == null) {
                throw BusinessException.of("微服务调用失败：" + response.status());
            }
            String body = new String(Util.toByteArray(response.body().asInputStream()), StandardCharsets.UTF_8);
            log.info("feign error response body:{}", body);
            TextJsonContainer jsonContainer = new TextJsonContainer(body);
            String message = jsonContainer.readJson("$.msg", String.class);
            int code = jsonContainer.readJson("$.code", Integer.class);
            throw BusinessException.of(code, message);
        } catch (Exception e) {
            return e;
        }
    }

}
