package com.codessquad.qna.exception;

public class InsufficientAuthenticationException extends RuntimeException {
    public InsufficientAuthenticationException() {
        super("권한이 없습니다.");
    }

    public InsufficientAuthenticationException(String message) {
        super(message);
    }

    public InsufficientAuthenticationException(Throwable e) {
        super(e);
    }

    public InsufficientAuthenticationException(String message, Throwable e) {
        super(message, e);
    }
}
