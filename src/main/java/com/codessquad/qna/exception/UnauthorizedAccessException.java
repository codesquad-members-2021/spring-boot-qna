package com.codessquad.qna.exception;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
        super("회원정보 수정에 실패했습니다.");
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
