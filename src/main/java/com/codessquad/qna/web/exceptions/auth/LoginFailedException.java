package com.codessquad.qna.web.exceptions.auth;

public class LoginFailedException extends RuntimeException {
    public static final String ERROR_MESSAGE = "아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.";

    public LoginFailedException() {
        super(ERROR_MESSAGE);
    }
}
