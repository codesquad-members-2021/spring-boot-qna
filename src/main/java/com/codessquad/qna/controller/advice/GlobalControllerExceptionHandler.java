package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotLoggedInException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(LoginFailedException loginFailedException, Model model){
        model.addAttribute("errorMessage", loginFailedException.getMessage());
        return "user/login";
    }

    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn(NotLoggedInException notLoggedInException, Model model) {
        model.addAttribute("errorMessage", notLoggedInException.getMessage());
        return "/user/login";
    }
}
