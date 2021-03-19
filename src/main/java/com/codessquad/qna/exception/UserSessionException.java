package com.codessquad.qna.exception;

public class UserSessionException extends RuntimeException {

    public UserSessionException(String errorMessage) {
        super(errorMessage);
    }

}
