package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView handleLoginFailed() {

        return new ModelAndView("user/login", "loginFailed", true);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound() {

        return "error/404";
    }

    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn() {
        return "";
    }


    @ExceptionHandler(SessionException.class)
    public String handleSessionOut() {

        return "error/404";
    }

}
