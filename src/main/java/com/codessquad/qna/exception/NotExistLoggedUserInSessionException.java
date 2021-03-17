package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotExistLoggedUserInSessionException extends RuntimeException {

    public NotExistLoggedUserInSessionException() {
        super("세션에 로그인한 사용자가 없습니다.");
    }

}
