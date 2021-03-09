package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super("수정 및 삭제 권한이 없습니다.");
    }
}
