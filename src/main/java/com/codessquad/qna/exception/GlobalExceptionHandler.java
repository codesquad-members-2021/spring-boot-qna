package com.codessquad.qna.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

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

    @ExceptionHandler(UserAccountException.class)
    private String handleUserAccountException(Model model, HttpSession session, HttpServletRequest request, UserAccountException e) {
        model.addAttribute("errorMessage", e.getMessage());
        if (isLoginUser(session)) {
            model.addAttribute("user", getUserFromSession(session));
        }
        return (String) request.getAttribute("path");
    }

    @ExceptionHandler(WriterOfAnswerListNotMatchException.class)
    private String handleWriterOfAnswerListNotMatchException(WriterOfAnswerListNotMatchException e) {
        return "redirect:/question/" + e.getQuestionId();
    }

}
