package com.codessquad.qna.web.exception;

public class FailedLoginException extends RuntimeException {

    public FailedLoginException() {
        super("Login failed, please try again");
    }

    public FailedLoginException(String msg) {
        super(msg);
    }

    public FailedLoginException(String msg, Throwable t) {
        super(msg, t);
    }
}
