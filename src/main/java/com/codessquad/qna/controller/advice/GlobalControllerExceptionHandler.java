package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLogin() {
        return "redirect:/users/login";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed() {
        return "redirect:/users/loginFailed";
    }
}
