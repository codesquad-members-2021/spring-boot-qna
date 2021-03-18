package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IllegalStateException.class)
    public String handleUnauthorizedAccess(IllegalStateException illegalStateException, Model model) {
        model.addAttribute("errorMessage", illegalStateException.getMessage());
        return "error/401";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e, Model model) {
        String message = e.getMessage();
        String[] error = message.split("\'");

        model.addAttribute("errorMessage",error[1]);
        return "error/402";
    }

}
