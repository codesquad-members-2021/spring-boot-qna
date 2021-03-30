package com.codessquad.qna.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("해당 정보를 발견하지 못했습니다.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
