package com.codessquad.qna.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(LoginFailedException.class)
    public String loginFailed(LoginFailedException exception, Model model) {
        return errorPageSender(exception, model);
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFoundException(NotFoundException exception, Model model) {
        return errorPageSender(exception, model);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(UnauthorizedException exception, Model model) {
        return errorPageSender(exception, model);
    }

    @ExceptionHandler(UnacceptableDuplicationException.class)
    public String unacceptableDuplicationException(UnacceptableDuplicationException exception, Model model) {
        return errorPageSender(exception, model);
    }

    private void initializeModel(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        logger.warn("설명 :", exceptionMessage);
        model.addAttribute("errorMessage", exceptionMessage);
    }

    private String errorPageSender(Exception exception, Model model) {
        initializeModel(exception, model);
        return "/error/error_page";
    }

}
