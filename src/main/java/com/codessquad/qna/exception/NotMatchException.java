package com.codessquad.qna.exception;

public class NotMatchException extends RuntimeException {

    public NotMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
