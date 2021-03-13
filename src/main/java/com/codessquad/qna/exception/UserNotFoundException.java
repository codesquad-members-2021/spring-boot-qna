package com.codessquad.qna.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        this("존재하지 않는 사용자입니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException (String message, Throwable cause) {
        super(message, cause);
    }
}
