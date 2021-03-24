package com.codessquad.qna.exception;

public class InsufficientAuthenticationException extends RuntimeException {

    public InsufficientAuthenticationException(String message) {
        super(message);
    }

    public InsufficientAuthenticationException(String message, Throwable e) {
        super(message, e);
    }

}
