package com.codessquad.qna.exception;

import com.codessquad.qna.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @ExceptionHandler(UserNotFoundException.class)
    protected String handleUserNotFoundException() {
        logger.info("사용자가 존재하지 않습니다.");
        return "redirect:/users/loginForm";
    }
}
