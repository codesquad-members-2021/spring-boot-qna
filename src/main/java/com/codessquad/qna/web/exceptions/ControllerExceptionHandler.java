package com.codessquad.qna.web.exceptions;

import com.codessquad.qna.web.exceptions.answers.AnswerNotFoundException;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.questions.QuestionNotFoundException;
import com.codessquad.qna.web.exceptions.users.NoLoginUserException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException exception, Model model) {
        initializeModel("허가되지 않은 접근이 발견되었습니다!", exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(NoLoginUserException.class)
    public String handleNoLoginUserException(NoLoginUserException exception, Model model) {
        initializeModel("현재 로그인된 상태가 아닙니다", exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException exception, Model model) {
        initializeModel("요청한 사용자 정보를 찾을 수 없습니다.", exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public String handleQuestionNotFoundException(QuestionNotFoundException exception, Model model) {
        initializeModel("요청한 질문 정보를 찾을 수 없습니다.", exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public String handleAnswerNotFoundException(AnswerNotFoundException exception, Model model) {
        initializeModel("요청한 답변 정보를 찾을 수 없습니다.", exception, model);
        return "/error/global-error";
    }

    @ExceptionHandler(LoginFailedException.class)
    public String handleLoginFailedException(LoginFailedException exception) {
        logException("로그인에 실패했습니다", exception);
        return "redirect:/users/login-form";
    }

    @ExceptionHandler(InvalidEntityException.class)
    public String handleInvalidEntityException(InvalidEntityException exception, Model model) {
        initializeModel("비어있는 항목이 있습니다!", exception, model);
        return "/error/global-error";
    }

    private void initializeModel(String errorMessage, Exception exception, Model model) {
        logException(errorMessage, exception);
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("extraErrorMessage", exception.getMessage());
    }

    private void logException(String errorMessage, Exception exception) {
        logger.warn(errorMessage);
        exception.printStackTrace();
    }
}
