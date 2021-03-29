package com.codessquad.qna.web.exception;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException() {
        super("해당 기능에 대한 접근 권한이 없습니다");
    }

    public IllegalAccessException(String message) {
        super(message);
    }
}
