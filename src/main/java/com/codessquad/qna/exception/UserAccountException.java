package com.codessquad.qna.exception;

import com.codessquad.qna.utils.ErrorMessage;

public class UserAccountException extends RuntimeException {

    private ErrorMessage errorMessage;

    public UserAccountException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getUserAccountError() {
        return errorMessage;
    }
}
