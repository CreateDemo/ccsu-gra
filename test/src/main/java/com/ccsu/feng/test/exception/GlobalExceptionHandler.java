package com.ccsu.feng.test.exception;

import com.ccsu.feng.test.enums.ResultEnum;
import com.ccsu.feng.test.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author admin
 * @create 2020-02-12-22:11
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result globalExceptionHandler(Exception e){
        log.error("相关异常-->{}",e.getMessage());
       return Result.error(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg());
    }
}
