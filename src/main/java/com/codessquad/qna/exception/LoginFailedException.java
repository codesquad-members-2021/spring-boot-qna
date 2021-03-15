package com.codessquad.qna.exception;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super("로그인에 실패했습니다.");
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(Throwable e) {
        super("로그인에 실패했습니다.", e);
    }

    public LoginFailedException(String message, Throwable e) {
        super(message, e);
    }
}
