package com.codessquad.qna.config;

import com.codessquad.qna.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandlers {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler() {
        return "/error/404";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundExceptionHandler() {
        return "/user/login_failed";
    }
}
