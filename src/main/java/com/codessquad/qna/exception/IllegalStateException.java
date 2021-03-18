package com.codessquad.qna.exception;

public class IllegalStateException extends RuntimeException {

    public IllegalStateException() {
        super("Unauthorized Access");
    }

    public IllegalStateException(String message) {
        super(message);
    }

}
