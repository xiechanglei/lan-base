package io.github.xiechanglei.lan.base.web.advice;

import lombok.Data;
/**
 * 字段错误信息
 */
@Data
public class FieldErrorInfo {
    private String filedName;
    private String errorMessage;
}
