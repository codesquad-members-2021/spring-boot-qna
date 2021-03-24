package com.codessquad.qna.exception;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("권한이 없습니다.");
    }

    public NotAuthorizedException(String msg) {
        super(msg);
    }
}
