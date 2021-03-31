package com.codessquad.qna.exception;

import com.codessquad.qna.utils.ErrorMessage;
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

    @ExceptionHandler(UserAccountException.class)
    private String handleUserAccountException(Model model, UserAccountException e) {
        model.addAttribute("errorMessage", e.getMessage());
        if (e.getUserAccountError() == ErrorMessage.LOGIN_FAILED) {
            return "user/loginForm";
        }
        return "user/userSignup";
    }
}
