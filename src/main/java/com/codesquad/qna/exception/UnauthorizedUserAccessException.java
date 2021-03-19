package com.codesquad.qna.exception;

public class UnauthorizedUserAccessException extends RuntimeException {
    public UnauthorizedUserAccessException() {
        super("Unauthorized user cannot access the contents.");
    }

    public UnauthorizedUserAccessException(String message) {
        super(message);
    }
}
