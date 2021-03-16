package com.codessquad.qna.user.ui;

import com.codessquad.qna.common.LoggerRepository;
import com.codessquad.qna.user.exception.LoginFailedException;
import com.codessquad.qna.user.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    private final LoggerRepository loggerRepository = new LoggerRepository(UserExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(LoginFailedException e) {
        loggerRepository.saveError(e);
        return "redirect:/loginFailed";
    }
}
