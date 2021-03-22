package com.codessquad.qna.web.exception;

public class LoginFailException extends RuntimeException {
    public LoginFailException() {
        super("로그인에 실패했습니다");
    }

    public LoginFailException(String message) {
        super(message);
    }
}
