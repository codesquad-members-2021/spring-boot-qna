package com.codessquad.qna.exception;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super("수정할 수 있는 권한이 없습니다.");
    }
}
