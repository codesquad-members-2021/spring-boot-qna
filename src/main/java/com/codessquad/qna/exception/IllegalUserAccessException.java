package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super("자신의 정보만 수정 가능합니다");
    }
}
