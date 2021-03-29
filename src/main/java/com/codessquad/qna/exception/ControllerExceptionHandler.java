package com.codessquad.qna.exception;


import com.sun.org.apache.xpath.internal.operations.Mod;
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
        initializeModel(exception, model);
        return "/error/login_failed";
    }

    @ExceptionHandler(NotFoundException.class)
    public String notFoundException(NotFoundException exception, Model model) {
        initializeModel(exception, model);
        return "/error/error_page";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(UnauthorizedException exception, Model model) {
        initializeModel(exception, model);
        return "/error/error_page";
    }

    @ExceptionHandler(UnacceptableDuplicationException.class)
    public String unacceptableDuplicationException(UnacceptableDuplicationException exception, Model model) {
        initializeModel(exception, model);
        return "/error/error_page";
    }

    private void initializeModel(Exception exception, Model model) {
        String exceptionMessage = exception.getMessage();
        logger.warn(exceptionMessage);
        model.addAttribute("errorMessage", exceptionMessage);
    }

}
