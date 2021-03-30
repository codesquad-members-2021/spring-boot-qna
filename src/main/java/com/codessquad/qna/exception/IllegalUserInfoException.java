package com.codessquad.qna.exception;

public class IllegalUserInfoException extends RuntimeException {
    public IllegalUserInfoException() {
        super("수정할 수 있는 권한이 없습니다.");
    }
}
