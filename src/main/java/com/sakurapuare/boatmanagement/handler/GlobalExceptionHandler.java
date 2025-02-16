package com.sakurapuare.boatmanagement.handler;

import com.sakurapuare.boatmanagement.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.sakurapuare.boatmanagement.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        // e.printStackTrace();
        log.error("全局异常：", e);
        return Response.error(e.getMessage());
    }
}
