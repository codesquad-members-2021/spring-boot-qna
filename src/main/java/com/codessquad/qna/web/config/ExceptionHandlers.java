package com.codessquad.qna.web.config;

import com.codessquad.qna.web.exception.UnAuthenticatedLoginException;
import com.codessquad.qna.web.exception.QuestionNotFoundException;
import com.codessquad.qna.web.exception.UnauthorizedUserException;
import com.codessquad.qna.web.exception.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(UnauthorizedUserException.class)
    public String handleUserException(UnauthorizedUserException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(UnAuthenticatedLoginException.class)
    public String handleLoginFailure(UnAuthenticatedLoginException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/login_failed";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionSearchFailure(QuestionNotFoundException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UnauthorizedUserException e, Model model) {
        model.addAttribute("exceptionMessage", e.getLocalizedMessage());
        return "user/error";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String blankNotAllowed(ConstraintViolationException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }
}
