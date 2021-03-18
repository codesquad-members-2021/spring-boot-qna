package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalStateException.class)
    public String handleUnauthorizedAccess(IllegalStateException illegalStateException, Model model) {
        model.addAttribute("errorMessage", illegalStateException.getMessage());
        return "error/401";
    }

}
