package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(EntryNotFoundException.class)
    private String handleEntryNotFoundException() {
        return "redirect:/questions";
    }
}
