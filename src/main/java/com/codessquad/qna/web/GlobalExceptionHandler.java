package com.codessquad.qna.web;

import com.codessquad.qna.web.exception.*;
import com.codessquad.qna.web.exception.IllegalAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalEntityIdException.class)
    public String handleIllegalEntityId(IllegalEntityIdException e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/errorPage";
    }

    @ExceptionHandler(LoginFailException.class)
    public String handleLoginFailException(LoginFailException e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/loginWithError";
    }

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLoginException(NotLoginException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/loginWithError";
    }

    @ExceptionHandler(IllegalAccessException.class)
    public String handleIllegalAccessException(IllegalAccessException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/errorPage";
    }

    @ExceptionHandler(DuplicatedUserIdException.class)
    public String handleDuplicatedUserId(DuplicatedUserIdException e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/formWithError";
    }
}
