package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler({UserNotFoundException.class, QuestionNotFoundException.class})
    private String handleQuestionNotFoundException() {
        return "redirect:/questions";
    }
}
