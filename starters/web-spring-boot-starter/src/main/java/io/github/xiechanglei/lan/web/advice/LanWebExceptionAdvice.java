package io.github.xiechanglei.lan.web.advice;

import io.github.xiechanglei.lan.beans.exception.BusinessException;
import io.github.xiechanglei.lan.beans.message.WebResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;

/**
 * <pre>
 * 全局异常处理,默认开启,如果不需要可以在配置文件中关闭，
 * 将异常转换为统一的返回结果WebResult
 * </pre>
 */
@Log4j2
@RestControllerAdvice
@ConditionalOnProperty(prefix = "lan.web.advice", name = "exception", havingValue = "true", matchIfMissing = true)
public class LanWebExceptionAdvice {

    @PostConstruct
    public void init() {
        log.info("开启全局异常处理 (lan.web.advice.exception.enable=true)...");
    }

    /**
     * 处理业务异常
     *
     * @param e 业务异常，包含了错误码和错误信息
     * @return WebResult结构化返回结果
     */
    @ExceptionHandler(value = BusinessException.class)
    public WebResult<?> handleException(BusinessException e) {
        return WebResult.failed(e.getCode(), e.getMessage());
    }


    /**
     * 处理方法参数校验失败异常（如 @Valid 和 @NotBlank）
     * @param e 方法参数校验失败异常
     * @return WebResult结构化返回结果
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public WebResult<?> handleValidationExceptions(ConstraintViolationException e) {
        FieldErrorInfo fieldErrorInfo = new FieldErrorInfo();
        e.getConstraintViolations().forEach(cv -> {
            String filePath = cv.getPropertyPath().toString();
            String filedName = filePath.substring(filePath.lastIndexOf(".") + 1);
            fieldErrorInfo.setFiledName(filedName);
            fieldErrorInfo.setErrorMessage(cv.getMessage());
        });
        String msg = "field:" + fieldErrorInfo.getFiledName() + ",msg:" + fieldErrorInfo.getErrorMessage();
        return WebResult.failed(-1, msg);

    }

    /**
     * <pre>
     * 理论上来说，所有的异常都应该分门别类的被处理，这样可以更加精准的定位问题，
     * 这里handle了Exception，为所有没有被处理的异常进行兜底处理，
     * 实际开发中，应该尽量减少到这里的异常出现的频率
     * </pre>
     */
    @ExceptionHandler(value = Exception.class)
    public WebResult<?> handleException(Exception e) {
        return WebResult.failed(-1, e.getMessage());
    }


}
