package com.codessquad.qna.web.exception;

public class CRUDAuthenticationException extends RuntimeException {

    public CRUDAuthenticationException(String msg) {
        super(msg);
    }

    public CRUDAuthenticationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
