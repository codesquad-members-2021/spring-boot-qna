package com.codessquad.qna.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler({NullPointerException.class, IllegalArgumentException.class})
    public String loginException() {
        return "/user/login";
    }

    @ExceptionHandler(IllegalUserUpdateException.class)
    public String userUpdateException(IllegalUserUpdateException e) {
        return "redirect:/users/" + e.getMessage() + "/form";
    }

    @ExceptionHandler({IllegalUserAccessException.class})
    public String noSameUser() {
        return "redirect:/";
    }
}
