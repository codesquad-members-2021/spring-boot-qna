package com.codessquad.qna.web.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
