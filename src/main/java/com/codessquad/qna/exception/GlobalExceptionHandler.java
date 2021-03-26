package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(EntityNotFoundException.class)
    private String handleEntityNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(InvalidSessionException.class)
    private String handleInvalidSessionException(Model model, InvalidSessionException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "user/loginForm";
    }
}
