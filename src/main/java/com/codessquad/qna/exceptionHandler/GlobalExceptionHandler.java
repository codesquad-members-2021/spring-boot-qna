package com.codessquad.qna.exceptionHandler;

import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.QuestionNotFoundException;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.exception.UserNotFoundInSessionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UserNotFoundException.class,
            QuestionNotFoundException.class
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
