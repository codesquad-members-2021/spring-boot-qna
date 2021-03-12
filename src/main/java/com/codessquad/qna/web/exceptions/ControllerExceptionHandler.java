package com.codessquad.qna.web.exceptions;

import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.exceptions.users.NoLoginUserException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedAccessException() {
        logger.error("허가되지 않은 접근이 발견됨!");
        return "redirect:/";
    }

    @ExceptionHandler(NoLoginUserException.class)
    public String handleNoLoginUserException() {
        logger.error("현재 로그인된 상태가 아님");
        return "redirect:/";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException() {
        logger.error("요청한 사용자 정보를 찾을 수 없음");
        return "redirect:/";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException() {
        logger.error("요청한 질문 정보를 찾을 수 없음");
        return "redirect:/";
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public String handleAnswerNotFoundException() {
        logger.error("요청한 답변 정보를 찾을 수 없음");
        return "redirect:/";
    }

}
