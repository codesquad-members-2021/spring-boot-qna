package com.codessquad.qna.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("입력하신 아이디의 사용자를 찾을 수 없습니다.");
    }
}

