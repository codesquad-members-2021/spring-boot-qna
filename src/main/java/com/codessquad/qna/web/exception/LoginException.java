package com.codessquad.qna.web.exception;

public class LoginException extends RuntimeException{
    private static final String WRONG_ID_OR_PASSWORD = "Id 또는 비밀번호가 틀렸습니다.";

    public LoginException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public LoginException() { this(WRONG_ID_OR_PASSWORD); }
}
