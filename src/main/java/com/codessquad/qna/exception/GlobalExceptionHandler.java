package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException(Model model, UserNotFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    private String handleQuestionNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException(Model model, IllegalUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(FailedUserLoginException.class)
    private String handleFailedUserLoginException(Model model, FailedUserLoginException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(DuplicateUserIdFoundException.class)
    private String handleDuplicateUserIdFoundException(Model model, DuplicateUserIdFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

}

