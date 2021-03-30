package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.getUserFromSession;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(EntityNotFoundException.class)
    private String handleEntityNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException(Model model, IllegalUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "user/loginForm";
    }

    @ExceptionHandler(UserAccountException.class)
    private String handleUserAccountException(Model model, HttpSession session, UserAccountException e) {
        model.addAttribute("errorMessage", e.getMessage());
        switch (e.getUserAccountError()) {
            case LOGIN_FAILED:
                return "user/loginForm";
            case DUPLICATED_ID:
                return "user/userSignup";
            case WRONG_PASSWORD:
                model.addAttribute("user", getUserFromSession(session));
                return "user/userUpdateForm";
            default:
                return "redirect:/";
        }
    }
}
