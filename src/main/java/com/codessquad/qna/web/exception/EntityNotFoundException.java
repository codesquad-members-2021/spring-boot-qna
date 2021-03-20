package com.codessquad.qna.web.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
