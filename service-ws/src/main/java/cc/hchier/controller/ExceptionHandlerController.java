package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.enums.ResponseCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author by Hchier
 * @Date 2023/2/12 19:57
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return RestResponse.build(ResponseCode.INVALID_PARAM,
            "MethodArgumentNotValidException: " + e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
    }

    @ExceptionHandler(Exception.class)
    public RestResponse<Object> handleException(Exception e) {
        return RestResponse.build(999, e.getCause().getMessage(), e.toString());
    }
}