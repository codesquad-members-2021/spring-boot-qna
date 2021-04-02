package com.codessquad.qna.web.config;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.exception.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler({UnauthorizedUserException.class, QuestionNotFoundException.class,
            UserNotFoundException.class, AnswerNotFoundException.class})
    public String handleUserException(QnaException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }

    @ExceptionHandler(UnAuthenticatedLoginException.class)
    public String handleLoginFailure(UnAuthenticatedLoginException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/login_failed";
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public String blankNotAllowed(ConstraintViolationException e, Model model) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "user/error";
    }
}
