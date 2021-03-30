package com.codessquad.qna.exception;

public class UserNotFoundException extends RuntimeException {

    public static final String USER_NOT_FOUND = "해당 사용자를 찾을 수 없습니다. id : ";

    public UserNotFoundException(Long id) {
        super(USER_NOT_FOUND + id);
    }

}
