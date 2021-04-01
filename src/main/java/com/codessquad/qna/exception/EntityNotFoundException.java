package com.codessquad.qna.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
    }
}
