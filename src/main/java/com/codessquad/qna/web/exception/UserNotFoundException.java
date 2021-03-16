package com.codessquad.qna.web.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
