package com.codessquad.qna.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String userId) {
        super(userId + " 인 사용자 Id가 이미 존재합니다.");
    }
}
