package com.codessquad.qna.controller.advice;

import com.codessquad.qna.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizationException.class)
    public String handleNotAuthorization(
            NotAuthorizationException notAuthorizationException, Model model) {
        model.addAttribute("errorMessage", notAuthorizationException.getMessage());
        return "error/401";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnotherAnswerException.class)
    public String HandleAnotherAnswer(AnotherAnswerException anotherAnswerException, Model model) {
        model.addAttribute("errorMessage", anotherAnswerException.getMessage());
        return "/error/400";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongPasswordException.class)
    public String handleWrongPassword(WrongPasswordException wrongPasswordException, Model model) {
        model.addAttribute("errorMessage", wrongPasswordException.getMessage());
        return "/error/400";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException userNotFoundException, Model model) {
        model.addAttribute("errorMessage", userNotFoundException.getMessage());
        return "/error/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AnswerNotFoundException.class)
    public String handleAnswerNotFound(AnswerNotFoundException answerNotFoundException, Model model) {
        model.addAttribute("errorMessage", answerNotFoundException.getMessage());
        return "/error/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleNotFound(QuestionNotFoundException QuestionNotFoundException, Model model) {
        model.addAttribute("errorMessage", QuestionNotFoundException.getMessage());
        return "/error/404";
    }

}
