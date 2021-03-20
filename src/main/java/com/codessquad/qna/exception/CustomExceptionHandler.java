package com.codessquad.qna.exception;

import com.codessquad.qna.exception.type.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


//INFO. 임시로 예외에 따른 대부분의 페이지는 404 페이지로 이동하게 하였습니다.

@ControllerAdvice
public class CustomExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(DuplicateException.class)
    public String DuplicateException(Model model) {
        model.addAttribute("DuplicateException", true); // 중복아이디
        return "user/form";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IncorrectAccountException.class)
    public String NotFoundId(IncorrectAccountException e, Model model) {
        if (e.getMessage().equals("loginFail")) {
            return "user/login_failed";
        } else {
            model.addAttribute("errorMessage", e.getMessage());
            return "error/404";
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthorizedException.class)
    public String UnauthorizedException(Model model) {
        model.addAttribute("errorMessage", "권한이 없습니다.");
        return "error/404";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotDeleteException.class)
    public String NotDeleteException(Model model) {
        model.addAttribute("errorMessage", "모든 답변이 삭제돼야, 질문을 삭제할 수 있습니다.");
        return "error/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String NotFoundException() {
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(IllegalArgumentException e, Model model) {
        model.addAttribute("serverError", e.getMessage());
        return "error/500";
    }
}
