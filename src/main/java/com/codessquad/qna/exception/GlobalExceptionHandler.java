package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    private String handleCurrentPasswordNotMatchException(Model model, CurrentPasswordNotMatchException e) {
        model.addAttribute("user", e.getUser());
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/updateForm";
    }

    @ExceptionHandler(IllegalUserAccessException.class)
    private String handleIllegalUserAccessException(Model model, IllegalUserAccessException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UserNotLoginException.class)
    private String handleUserNotLoginException(Model model, UserNotFoundException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(UserNotFoundException.class)
    private String handleUserNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(WriterOfAnswerListNotMatchException.class)
    private String handleWriterOfAnswerListNotMatchException(WriterOfAnswerListNotMatchException e) {
        return "redirect:/question/" + e.getQuestionId();
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    private String handleQuestionNotFoundException() {
        return "redirect:/";
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    private String handleAnswerNotFoundException() {
        return "redirect:/";
    }

}
