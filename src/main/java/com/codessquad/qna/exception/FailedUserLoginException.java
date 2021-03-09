package com.codessquad.qna.exception;

public class FailedUserLoginException extends RuntimeException {
    public FailedUserLoginException() {
        super("로그인이 필요합니다.");
    }

    public FailedUserLoginException(String message) {
        super(message);
    }
}

