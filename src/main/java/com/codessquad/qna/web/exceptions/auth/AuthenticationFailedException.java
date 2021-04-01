package com.codessquad.qna.web.exceptions.auth;

public class AuthenticationFailedException extends RuntimeException {
    public static final String PASSWORD_NOT_MATCHING = "패스워드가 일치하지 않습니다";

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
