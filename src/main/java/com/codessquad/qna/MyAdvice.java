package com.codessquad.qna;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdvice {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFountException() {
        return "notExistHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "unableToAccessToOthers";
    }
}
