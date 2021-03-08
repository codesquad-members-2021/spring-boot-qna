package com.codessquad.qna.controller;

import com.codessquad.qna.exception.CanNotFindPostException;
import com.codessquad.qna.exception.CanNotFindUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 잘못된 입력값이 들어왔을때 핸들링 해주는 메소드
     * 후에 공부해서 에러페이지를 만들어서 처리해야할듯함
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(Exception e, Model model) {
        handleException(e, model);
        return "error";
    }

    /**
     * 유저를 가져오지 못할시 처리할 로직 생성
     * 일단 ErrorPage 로 ErrorMessage 리턴해줌
     * @return
     */
    @ExceptionHandler(CanNotFindUserException.class)
    public String handleCanNotFindUserException(Exception e, Model model) {
        handleException(e, model);
        return "error";
    }

    /**
     * Post 를 찾지 못할시 해당 에러메세지를 에러페이지로 리턴한다.
     * @param e
     * @param model
     * @return handleCanNotFindPostException message to error page
     */
    @ExceptionHandler(CanNotFindPostException.class)
    public String handleCanNotFindPostException(Exception e, Model model) {
        handleException(e, model);
        return "error";
    }

    private void handleException(Exception e, Model model) {
        logger.error(e.getMessage());
        model.addAttribute("exception", e);
    }

}
