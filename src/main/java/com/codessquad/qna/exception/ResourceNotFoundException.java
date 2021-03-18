package com.codessquad.qna.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("해당 항목을 찾을 수 없습니다.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable e) {
        super("해당 항목을 찾을 수 없습니다.", e);
    }

    public ResourceNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
