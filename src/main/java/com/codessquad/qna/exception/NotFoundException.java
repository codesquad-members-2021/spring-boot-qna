package com.codessquad.qna.exception;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class NotFoundException extends RuntimeException{
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    public String notFoundException(NotFoundException exception, Model model) {
        logger.warn("notFoundException occur");
        return "redirect:/";
    }

}
