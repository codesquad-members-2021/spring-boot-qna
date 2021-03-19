package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.exception.LoginFailedException;
import com.codessquad.qna.user.exception.NotLoggedInException;
import com.codessquad.qna.user.exception.UnauthorizedException;
import com.codessquad.qna.user.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

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

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity handleUnauthorized(UnauthorizedException e) {
        logger.error(e.getMessage());
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
