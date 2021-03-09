package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    protected String handleUserNotFoundException() {
        return "redirect:/users/loginForm";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    protected String handleQuestionNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    protected String handleIllegalUserAccessException(Model model, IllegalUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(FailedUserLoginException.class)
    public String handleFailedUserLoginException(Model model, FailedUserLoginException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }
}
