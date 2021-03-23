package com.codessquad.qna.web.exception;

public class UnAuthenticatedLoginException extends RuntimeException{
    private static final String WRONG_ID_OR_PASSWORD = "Id 또는 비밀번호가 틀렸습니다.";

    public UnAuthenticatedLoginException() { this(WRONG_ID_OR_PASSWORD); }

    public UnAuthenticatedLoginException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
