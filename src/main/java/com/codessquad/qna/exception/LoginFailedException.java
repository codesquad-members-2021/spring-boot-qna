package com.codessquad.qna.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("Login Failed");
    }
}
