package com.codessquad.qna.controller;

import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.exception.NotExistLoggedUserInSessionException;
import com.codessquad.qna.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 유저를 가져오지 못할시 처리할 로직 생성
     * 없는 페이지에 대한 요청이 므로 404 페이지를 리턴하도록 해줌
     * @return 404 page
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleCanNotFindUserException(UserNotFoundException e, Model model) {
        return "error/404";
    }

    /**
     * 없는 페이지에 대한 요청이 므로 404 페이지를 리턴하도록 해줌
     * @param e
     * @param model
     * @return 404 page
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CanNotFindPostException.class)
    public String handleCanNotFindPostException(CanNotFindPostException e, Model model) {
        return "error/404";
    }

    /**
     * 세션에 로그인 된 유저가 없는데 유저 권한의 활동을 하려할 시 발생하는 에러
     * 로그인 창으로 redirect
     * @return 401 Status code
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotExistLoggedUserInSessionException.class)
    public String handleNotExistLoggedUserInSession(NotExistLoggedUserInSessionException e, HttpServletResponse httpServletResponse) {
        logger.error(e.getMessage());
        return "user/login";
    }

    /**
     * 해당 권한이 있는 유저가 아닐시 index.html 창으로 redirect 시킨다.
     * @return 401 Status code
     */
    @ExceptionHandler(IllegalAccessException.class)
    public String handleIllegalAccessException(IllegalAccessException e) {
        logger.error(e.getMessage());
        return "redirect:/";
    }

}
