package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UnauthenticatedException.class)
    public String handleNotLoginException() {
        return "user/login_failed";
    }
}
