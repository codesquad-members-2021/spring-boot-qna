package com.codessquad.qna.exception;

public class UserSessionException extends RuntimeException {

    public UserSessionException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }

}
