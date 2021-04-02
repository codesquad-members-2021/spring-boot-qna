package com.codessquad.qna.exception;

public class LoginFailedException extends RuntimeException {
    public static final String FAILED_LOGIN = "로그인에 실패하였습니다";
    public LoginFailedException() {
        super(FAILED_LOGIN);
    }
}
