package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    private String handleEntityNotFoundException() {
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserSessionException.class)
    private String handleUserSessionException(Model model, UserSessionException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UserAccountException.class)
    private String handleUserAccountException(Model model, HttpSession session,  UserAccountException e) {
        model.addAttribute("errorMessage", e.getMessage());
        switch (e.getErrorMessage()) {
            case DUPLICATED_ID:
                return "/user/form";
            case LOGIN_FAILED:
                return "/user/login";
            case WRONG_PASSWORD:
                model.addAttribute("user", getUserFromSession(session));
                return "/user/updateForm";
            default:
                return "/";
        }
    }

}
