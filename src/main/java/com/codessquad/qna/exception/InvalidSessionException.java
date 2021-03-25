package com.codessquad.qna.exception;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
        super("접근 권한이 없는 유저입니다.");
    }
}
