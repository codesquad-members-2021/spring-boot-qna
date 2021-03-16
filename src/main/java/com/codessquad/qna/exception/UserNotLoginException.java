package com.codessquad.qna.exception;

public class UserNotLoginException extends RuntimeException {

    public UserNotLoginException() {
        super("로그인이 필요한 서비스 입니다.");
    }

}
