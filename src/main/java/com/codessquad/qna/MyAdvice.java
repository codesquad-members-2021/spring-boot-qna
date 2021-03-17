package com.codessquad.qna;

import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoSessionedUserException;
import com.codessquad.qna.exception.NoUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdvice {

    @ExceptionHandler({NoUserException.class, NoQuestionException.class})
    public String handleNotExistException() {
        return "notExistHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "unableToAccessToOthers";
    }

    @ExceptionHandler(NoSessionedUserException.class)
    public String handleNoSessionedUserException() {
        return "redirect:/users/loginForm";
    }
}
