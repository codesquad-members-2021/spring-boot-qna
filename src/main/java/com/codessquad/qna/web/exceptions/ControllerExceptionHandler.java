package com.codessquad.qna.web.exceptions;

import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.exceptions.users.NoLoginUserException;
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

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(NoLoginUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleNoLoginUserException(NoLoginUserException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
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

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException() {
        logger.warn("로그인에 실패했습니다");
        return "redirect:/users/login-form";
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidEntityException(InvalidEntityException exception, Model model) {
        initializeModel(exception, model);
        return "/error/global-error";
    }

    private void initializeModel(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        logger.warn(exceptionMessage);
        model.addAttribute("extraErrorMessage", exceptionMessage);
    }

}
