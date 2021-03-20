package com.codessquad.qna.web;

import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.exception.LoginFailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.FailedLoginException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalEntityIdException.class)
    public String handleIllegalEntityId(IllegalEntityIdException e, Model model) {
        //여기도 수정, user인지 qeustion인지
        logger.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errorPage";
    }

    @ExceptionHandler(LoginFailException.class)
    public String handleLoginFailException(LoginFailException e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "user/loginFailed";
    }
}
