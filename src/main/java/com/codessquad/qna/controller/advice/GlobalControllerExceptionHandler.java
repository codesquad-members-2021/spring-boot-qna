package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.JoinFailedException;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import com.codessquad.qna.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler({LoginFailedException.class, NotLoggedInException.class})
    public String handleLogin(RuntimeException runtimeException, Model model) {
        model.addAttribute("errorMessage", runtimeException.getMessage());
        return "user/login";
    }

    @ExceptionHandler(JoinFailedException.class)
    public String handleJoinFailed(JoinFailedException joinFailedException, Model model) {
        model.addAttribute("errorMessage", joinFailedException.getMessage());
        return "user/form";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccess(UnauthorizedAccessException unauthorizedAccessException, Model model) {
        model.addAttribute("errorMessage", unauthorizedAccessException.getMessage());
        return "user/update";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException notFoundException, Model model) {
        model.addAttribute("errorMessage", notFoundException.getMessage());
        return "error/404";
    }
}
