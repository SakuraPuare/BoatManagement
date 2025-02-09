package com.sakurapuare.boatmanagement.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sakurapuare.boatmanagement.common.Response;

@RestControllerAdvice(basePackages = "com.sakurapuare.boatmanagement.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        return Response.error(e.getMessage());
    }
}
