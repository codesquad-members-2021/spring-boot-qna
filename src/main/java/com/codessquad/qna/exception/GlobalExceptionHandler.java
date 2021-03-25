package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(EntryNotFoundException.class)
    private String handleEntryNotFoundException() {
        return "redirect:/questions";
    }

    @ExceptionHandler(InvalidSessionException.class)
    private String handleInvalidSessionException(Model model, InvalidSessionException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "user/loginForm";
    }
}
