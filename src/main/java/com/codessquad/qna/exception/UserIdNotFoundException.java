package com.codessquad.qna.exception;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException() {
        super("해당 Id의 사용자를 찾을 수 없습니다.");
    }
}
