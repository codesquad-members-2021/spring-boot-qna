package com.codessquad.qna.exceptionHandler;

import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.SaveFailedException;
import com.codessquad.qna.exception.UserNotFoundInSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiGlobalExceptionHandler {
    @ExceptionHandler(SaveFailedException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String saveFailedException(SaveFailedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundInSessionException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String userNotFoundInSessionException(UserNotFoundInSessionException ex) {
        return "로그인이 필요합니다.";
    }

    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String NotAuthorizedException(NotAuthorizedException ex) {
        return ex.getMessage();
    }
}
