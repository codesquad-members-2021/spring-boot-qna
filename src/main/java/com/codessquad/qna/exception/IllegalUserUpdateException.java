package com.codessquad.qna.exception;

public class IllegalUserUpdateException extends RuntimeException {
    public IllegalUserUpdateException(Long id) {
        super(String.valueOf(id));
    }
}
