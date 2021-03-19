package com.codesquad.qna.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Cannot find the user, because it is not registered.");
    }
}

