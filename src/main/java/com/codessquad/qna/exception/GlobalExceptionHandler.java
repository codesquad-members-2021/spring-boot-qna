package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateUserIdFoundException.class)
    private  String handleDuplicateUserIdFoundException(Model model, DuplicateUserIdFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/form";
    }

    @ExceptionHandler(IdOrPasswordNotMatchException.class)
    private String handleIdOrPasswordNotMatchException(Model model, IdOrPasswordNotMatchException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(CurrentPasswordNotMatchException.class)
    private String handleCurrentPasswordNotMatchException(Model model, CurrentPasswordNotMatchException e, HttpSession session) {
        model.addAttribute("user", getUserFromSession(session));
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/updateForm";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException(Model model, IllegalUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException() {
        return "redirect:/user/list";
    }

}
