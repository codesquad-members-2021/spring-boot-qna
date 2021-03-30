package com.codessquad.qna.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(FailedLoginException.class)
    public String handleNotLoginException() {
        return "redirect:/users/login-form";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNotFoundException(Exception e) {
        ModelAndView mav = new ModelAndView("error/notFoundError");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }

    @ExceptionHandler(CrudNotAllowedException.class)
    public ModelAndView handleCRUDAuthenticationException(Exception e) {
        ModelAndView mav = new ModelAndView("error/authenticationError");
        mav.addObject("errorMessage", e.getMessage());
        return mav;
    }


}
