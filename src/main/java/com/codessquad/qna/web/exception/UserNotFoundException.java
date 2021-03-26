package com.codessquad.qna.web.exception;

public class UserNotFoundException extends RuntimeException{
    public static final String USER_NOT_FOUND = "사용자가 존재하지 않습니다.";

    public UserNotFoundException() {
        this(USER_NOT_FOUND);
    }

    public UserNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}

