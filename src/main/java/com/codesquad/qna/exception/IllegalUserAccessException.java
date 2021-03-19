package com.codesquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super("Cannot access other user's information.");
    }
}
