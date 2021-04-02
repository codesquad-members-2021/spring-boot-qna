package com.codessquad.qna.exception;

public class CrudNotAllowedException extends RuntimeException {

    public CrudNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CrudNotAllowedException(String message) {
        super(message);
    }
}
