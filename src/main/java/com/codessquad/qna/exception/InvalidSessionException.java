package com.codessquad.qna.exception;

import com.codessquad.qna.utils.ErrorMessage;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
        super(ErrorMessage.INVALID_USER.getErrorMessage());
    }
}
