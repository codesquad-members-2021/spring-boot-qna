package com.codessquad.qna;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.exception.NoSessionedUserException;
import com.codessquad.qna.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
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

    @ExceptionHandler(IllegalAccessException.class)
    public Result handleIllegalAccessException() {
//        return "/exception/illegalAccessHandle";
        return Result.fail("자신의 글만 접근가능합니다.");
    }
}
