package com.codessquad.qna.exception;

import com.codessquad.qna.utils.ErrorMessage;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super(ErrorMessage.INVALID_USER.getErrorMessage());
    }
}
