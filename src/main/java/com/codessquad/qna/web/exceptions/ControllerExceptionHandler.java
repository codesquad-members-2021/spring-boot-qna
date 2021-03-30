package com.codessquad.qna.web.exceptions;

import com.codessquad.qna.web.exceptions.Entity.InvalidEntityException;
import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.AuthenticationFailedException;
import com.codessquad.qna.web.exceptions.auth.ForbiddenAccessException;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.exceptions.users.NoLoginUserException;
import com.codessquad.qna.web.exceptions.users.RequestToCreateDuplicatedUserException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccessException(ForbiddenAccessException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(NoLoginUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNoLoginUserException(NoLoginUserException exception, Model model) {
        initializeModel(exception, model);
        return "/error/login-failed";
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleLoginFailedException(LoginFailedException exception, Model model) {
        initializeModel(exception, model);
        return "/error/login-failed";
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleQuestionNotFoundException(QuestionNotFoundException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAnswerNotFoundException(AnswerNotFoundException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidEntityException(InvalidEntityException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAuthenticationFailedException(AuthenticationFailedException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(RequestToCreateDuplicatedUserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRequestToCreateDuplicatedUserException(RequestToCreateDuplicatedUserException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    private void initializeModel(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        logger.warn(exceptionMessage);
        model.addAttribute("extraErrorMessage", exceptionMessage);
    }
}
