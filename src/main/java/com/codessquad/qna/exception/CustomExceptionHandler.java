package com.codessquad.qna.exception;

import com.codessquad.qna.exception.type.*;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 68936@naver.com on 2021-03-18 오전 2:43
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(DuplicateException.class)
    public String DuplicateException(Model model){
        model.addAttribute("DuplicateException",true); // 중복아이디
        return "user/form";
    }
    @ExceptionHandler(IncorrectAccountException.class)
    public String NotFoundId(){
        return "user/login_failed";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(IllegalArgumentException e, Model model){
        model.addAttribute("serverError",e.getMessage());
        return "error/500";
    }
    @ExceptionHandler(UnauthorizedException.class)
    public String UnauthorizedException(Model model){
        model.addAttribute("errorMessage","권한이 없습니다.");
        return "error/404";
    }
    @ExceptionHandler(NotDeleteException.class)
    public String NotDeleteException(Model model){
        model.addAttribute("errorMessage","모든 답변이 삭제돼야, 질문을 삭제할 수 있습니다.");
        return "error/404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String NotFoundException(){
        return "error/404";
    }
}
