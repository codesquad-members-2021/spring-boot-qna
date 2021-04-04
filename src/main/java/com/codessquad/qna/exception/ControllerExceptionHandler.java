package com.codessquad.qna.exception;

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

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailed(LoginFailedException exception, Model model) {
        initializeModel(exception, model);
        return "/error/login_failed";
    }

    @ExceptionHandler(NotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String notLoggedInException(NotFoundException exception, Model model) {
        initializeModel(exception, model);
        return "redirect:/signup";
    }

    @ExceptionHandler({WrongInputException.class, UnauthorizedQuestionException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String unacceptableDuplicationException(RuntimeException exception, Model model) {
        initializeModel(exception, model);
        return "/error/error_page";//글로벌 에러 페이지를 쓰는경우?
    }

    private void initializeModel(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        logger.warn("설명 :", exceptionMessage);
        model.addAttribute("errorMessage", exceptionMessage);
    }


}
