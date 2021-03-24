package com.codessquad.qna.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String msg) {
        super(msg);
    }
}
