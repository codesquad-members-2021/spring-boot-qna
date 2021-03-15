package com.codessquad.qna.config;

import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.exception.LoginFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionHandlers {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler() {
        return "/error/404";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView loginFailedExceptionHandler(LoginFailedException e) {
        return new ModelAndView("/error/401", "errMessage", e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public String insufficientAuthenticationExceptionHandler() {
        return "/error/403";
    }
}
