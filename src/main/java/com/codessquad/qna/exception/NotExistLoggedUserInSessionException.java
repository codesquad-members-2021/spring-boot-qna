package com.codessquad.qna.exception;

public class NotExistLoggedUserInSessionException extends RuntimeException{

    public NotExistLoggedUserInSessionException() {
        super("세션에 로그인한 사용자가 없습니다.");
    }
}
