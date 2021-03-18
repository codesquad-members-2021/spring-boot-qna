package com.codessquad.qna.config;

import com.codessquad.qna.exception.InsufficientAuthenticationException;
import com.codessquad.qna.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CommonExceptionHandlers {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExceptionHandler(IllegalArgumentException e) {
        logger.error("IllegalArgumentException", e);

        return "/error/404";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView loginFailedExceptionHandler(LoginFailedException e) {
        logger.error("LoginFailedException", e);

        return new ModelAndView("/error/401", "errMessage", e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public String insufficientAuthenticationExceptionHandler(InsufficientAuthenticationException e) {
        logger.error("InsufficientAuthenticationException", e);

        return "/error/403";
    }
}
