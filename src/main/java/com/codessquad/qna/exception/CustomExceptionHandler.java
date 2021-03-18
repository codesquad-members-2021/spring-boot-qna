package com.codessquad.qna.exception;

import com.codessquad.qna.exception.type.DuplicateIdFoundException;
import com.codessquad.qna.exception.type.IncorrectAccountException;
import com.codessquad.qna.exception.type.UnauthorizedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by 68936@naver.com on 2021-03-18 오전 2:43
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(DuplicateIdFoundException.class)
    public String DuplicateIdFound(Model model){
        model.addAttribute("DuplicateIdFound",true); // 중복아이디
        return "user/form";
    }
    @ExceptionHandler(IncorrectAccountException.class)
    public String NotFoundId(){
        return "user/login_failed";
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentException(){
        return "error/404";
    }
    @ExceptionHandler(UnauthorizedException.class)
    public String UnauthorizedException(Model model){
        model.addAttribute("errorMessage","타인의 정보는 열람할 수 없습니다!");
        return "error/404";
    }
}
