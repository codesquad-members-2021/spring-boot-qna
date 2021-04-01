package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super(ErrorMessage.INVALID_USER.getErrorMessage());
    }
}
