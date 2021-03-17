package com.codessquad.qna.web.config;

import com.codessquad.qna.web.exception.LoginException;
import com.codessquad.qna.web.exception.UserException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UserException.class)
    public String handleUserException(UserException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(LoginException.class)
    public String handleLoginFailure(LoginException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/login_failed";
    }
}
