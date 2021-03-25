package com.codessquad.qna.exception;

public class NotLoginException extends RuntimeException {

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotLoginException(String message) {
        super(message);
    }
}
