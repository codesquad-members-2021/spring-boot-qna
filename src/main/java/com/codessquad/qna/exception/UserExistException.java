package com.codessquad.qna.exception;

public class UserExistException extends RuntimeException {

    public UserExistException() {
        super("User Already Exists");
    }
}
