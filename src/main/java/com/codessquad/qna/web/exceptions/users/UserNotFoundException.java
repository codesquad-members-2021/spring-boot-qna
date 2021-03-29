package com.codessquad.qna.web.exceptions.users;

public class UserNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "존재하지 않는 사용자 정보입니다!";

    public UserNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
