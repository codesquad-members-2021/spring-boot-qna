package com.codessquad.qna.exception;

public class NotAuthorizationException extends RuntimeException {

    public NotAuthorizationException() {
        super("권한이 없습니다.");
    }

    public NotAuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
