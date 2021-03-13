package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException(String message) {
        super(message);
    }
}
