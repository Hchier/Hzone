package xyz.hchier.hzone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.hchier.hzone.base.ResponseCode;
import xyz.hchier.hzone.base.RestResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by Hchier
 * @Date 2022/6/25 17:04
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {
    private ObjectMapper objectMapper;

    public ExceptionHandleController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorList = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        log.error(String.valueOf(errorList));
        return RestResponse.fail(ResponseCode.INVALID_DATA.getCode(), String.valueOf(errorList));
    }
}
