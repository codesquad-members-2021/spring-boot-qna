package com.codessquad.qna;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.exception.IllegalAccessToQuestionException;
import com.codessquad.qna.exception.IllegalAccessOnUpdate;
import com.codessquad.qna.exception.NoSessionedUserException;
import com.codessquad.qna.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "/exception/notFoundHandle";
    }

    @ExceptionHandler(IllegalAccessOnUpdate.class)
    public String handleIllegalAccessToUpdate(Model model, IllegalAccessOnUpdate e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/updateForm";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(Model model, IllegalStateException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(IllegalAccessToQuestionException.class)
    public String handleIllegalAccessToQuestionException(Model model, IllegalAccessToQuestionException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/exception/illegalAccessHandle";
    }

    @ExceptionHandler(NoSessionedUserException.class)
    public String handleNoSessionedUserException(Model model, NoSessionedUserException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ResponseBody
    @ExceptionHandler(IllegalAccessException.class)
    public Result handleIllegalAccessException() {
        return Result.fail("자신의 댓글만 접근가능합니다.");
    }
}
