package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdFoundException.class)
    private  String handleDuplicateUserIdFoundException(Model model, DuplicateUserIdFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/form";
    }

    @ExceptionHandler(IdOrPasswordNotMatchException.class)
    private String handleIdOrPasswordNotMatchException(Model model, IdOrPasswordNotMatchException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException() {
        return "redirect:/user/list";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException() {
        return "redirect:/user/login";
    }

}
