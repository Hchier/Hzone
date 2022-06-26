package xyz.hchier.hzone.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;

/**
 * @author by Hchier
 * @Date 2022/6/25 17:04
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return RestResponse.fail(ResponseCode.INVALID_DATA.getCode(), ResponseCode.INVALID_DATA.getMessage());
    }
}
