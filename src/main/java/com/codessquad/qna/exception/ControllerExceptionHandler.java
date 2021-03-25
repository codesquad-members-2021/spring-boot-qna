package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLoginException() {
        return "redirect:/users/loginForm";
    }
}
