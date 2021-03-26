package com.codessquad.qna.web;

import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        return "redirect:/users/loginForm";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    public String handleIllegalUserAccessException() {
        return "redirect:/";
    }
}
