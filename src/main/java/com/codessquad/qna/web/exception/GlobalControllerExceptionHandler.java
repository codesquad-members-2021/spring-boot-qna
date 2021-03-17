package com.codessquad.qna.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLoginException() {
        return "redirect:/users/login-form";
    }

    @ExceptionHandler({UserNotFoundException.class, QuestionNotFoundException.class, AnswerNotFoundException.class})
    public ModelAndView handleNotFoundException(Exception e) {
        ModelAndView mav = new ModelAndView("error/notFoundError");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(CRUDAuthenticationException.class)
    public ModelAndView handleCRUDAuthenticationException(Exception e) {
        ModelAndView mav = new ModelAndView("error/authenticationError");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }


}
