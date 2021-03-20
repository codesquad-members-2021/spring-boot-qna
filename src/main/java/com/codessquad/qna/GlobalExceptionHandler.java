package com.codessquad.qna;

import com.codessquad.qna.exception.NoSessionedUserException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotMatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "/exception/notFoundHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(Model model, IllegalStateException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(NoSessionedUserException.class)
    public String handleNoSessionedUserException(Model model, NoSessionedUserException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(NotMatchException.class)
    public String handleNotMatchException(Model model, NotMatchException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/updateForm";
    }
}
