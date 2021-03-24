package com.codessquad.qna.web.exceptions.auth;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
