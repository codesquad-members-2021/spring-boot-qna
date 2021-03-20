package com.codessquad.qna.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(Throwable e) {
        super("로그인에 실패했습니다.", e);
    }
}
