package com.codessquad.qna.exception;

public class UserAccountException extends RuntimeException {

    private ErrorMessage errorMessage;

    public UserAccountException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
