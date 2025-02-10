package com.sakurapuare.boatmanagement.handler;

import com.sakurapuare.boatmanagement.common.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.sakurapuare.boatmanagement.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        e.printStackTrace();
        return Response.error(e.getMessage());
    }
}
