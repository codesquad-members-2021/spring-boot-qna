package com.codessquad.qna.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("등록되지 않은 아이디입니다.");
    }
}

