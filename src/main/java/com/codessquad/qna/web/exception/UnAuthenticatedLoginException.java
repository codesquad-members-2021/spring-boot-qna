package com.codessquad.qna.web.exception;

public class UnAuthenticatedLoginException extends RuntimeException{
    public static final String WRONG_ID_OR_PASSWORD = "Id 또는 비밀번호가 틀렸습니다.";
    public static final String MUST_LOGIN = "로그인해주십시오.";

    public UnAuthenticatedLoginException() { this(WRONG_ID_OR_PASSWORD); }

    public UnAuthenticatedLoginException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
