package com.codessquad.qna.exception;

public class UserSessionException extends RuntimeException {

    public UserSessionException() {
        super("접근권한이 없는 사용자입니다.");
    }

}
