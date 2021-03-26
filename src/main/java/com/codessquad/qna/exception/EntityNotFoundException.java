package com.codessquad.qna.exception;

import com.codessquad.qna.utils.ErrorMessage;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }
}
