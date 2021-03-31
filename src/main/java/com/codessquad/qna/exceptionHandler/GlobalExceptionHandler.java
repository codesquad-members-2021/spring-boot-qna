package com.codessquad.qna.exceptionHandler;

import com.codessquad.qna.exception.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {
    @ExceptionHandler({
            NotFoundException.class,
            UserAlreadyExistException.class,
            CannotDeleteQuestionException.class,
            BindException.class,
            ConstraintViolationException.class
    })
    public String exceptionPage(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "/error/handledError";
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public String notAuthorizedException(NotAuthorizedException ex, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        model.addAttribute("errorMessage", ex.getMessage());
        return "/error/handledError";
    }

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public String userNotFoundInSessionException(UserNotFoundInSessionException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FOUND);
        return "redirect:/users/login";
    }
}
