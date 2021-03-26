package com.codessquad.qna.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entry) {
        super("해당 " + entry + "을(를) 찾을 수 없습니다.");
    }
}
