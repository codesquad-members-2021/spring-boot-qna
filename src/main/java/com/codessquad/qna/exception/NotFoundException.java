package com.codessquad.qna.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Not Found");
    }
}
