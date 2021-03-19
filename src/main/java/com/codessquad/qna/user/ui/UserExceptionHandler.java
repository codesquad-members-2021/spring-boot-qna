package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.exception.LoginFailedException;
import com.codessquad.qna.user.exception.NotLoggedInException;
import com.codessquad.qna.user.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(LoginFailedException e) {
        logger.error(e.getMessage());
        return "redirect:/loginFailed";
    }

    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn(NotLoggedInException e) {
        logger.error(e.getMessage());
        return "redirect:/login";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public void handleUnauthorized(UnauthorizedException e) {
        logger.error(e.getMessage());
    }
}
