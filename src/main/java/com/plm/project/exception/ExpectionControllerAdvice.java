package com.plm.project.exception;

import com.plm.project.common.BizCodeEnume;
import com.plm.project.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice("com.plm.project.controller")
public class ExpectionControllerAdvice {
    //处理校验异常统一处理
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Map<String, String>> handleException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(error.getObjectName(), error.getDefaultMessage());
        });
        return new R(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMessage(),errors);
    }
    //其他异常统一处理
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e) {
        log.error(BizCodeEnume.UNKNOW_EXCEPTION.getMessage(),e);
        return new R(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMessage(),e);
    }
}
