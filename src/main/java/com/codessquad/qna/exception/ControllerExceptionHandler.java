package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException() {
        return null;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(Exception e) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user/login");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;//"redirect:/user/login";
    }
}
