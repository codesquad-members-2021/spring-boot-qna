package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn() {
        return "redirect:/users/login";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(Model model) {
        model.addAttribute("loginFailed", true);
        return "users/login";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound() {
        return "error/404";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccess(UnauthorizedAccessException unauthorizedAccessException, Model model) {
        model.addAttribute("errorMessage", unauthorizedAccessException.getMessage());
        return "error/401";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistException.class)
    public String handleUserExist(Model model) {
        model.addAttribute("signUpFailed", true);
        return "users/form";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbidden(ForbiddenException forbiddenException, Model model) {
        model.addAttribute("errorMessage", forbiddenException.getMessage());
        return "error/403";
    }
}
