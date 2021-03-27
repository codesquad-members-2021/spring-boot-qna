package com.codessquad.qna.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("요청하신 정보를 찾지 못했습니다.");
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
