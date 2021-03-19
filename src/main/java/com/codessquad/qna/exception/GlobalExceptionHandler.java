package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private String handleNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(UserSessionException.class)
    private String handleUserSessionException(Model model, UserSessionException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

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
    private String handleCurrentPasswordNotMatchException(Model model, CurrentPasswordNotMatchException e) {
        model.addAttribute("user", e.getUser());
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/updateForm";
    }

    @ExceptionHandler(WriterOfAnswerListNotMatchException.class)
    private String handleWriterOfAnswerListNotMatchException(WriterOfAnswerListNotMatchException e) {
        return "redirect:/question/" + e.getQuestionId();
    }

}
