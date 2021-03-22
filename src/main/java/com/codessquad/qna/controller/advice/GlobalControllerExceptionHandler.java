package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.LoginFailedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailed(LoginFailedException loginFailedException, Model model){
        model.addAttribute("loginFailed", loginFailedException.getMessage());
        return "user/login";
    }
}
