package com.codessquad.qna.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("존재하지 않는 유저입니다; id: %d", id));
    }
}
