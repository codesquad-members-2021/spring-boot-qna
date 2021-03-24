package com.codessquad.qna.exception;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("로그인 사용자만 이용할 수 있는 기능입니다.");
    }

    public NotLoggedInException(String message) {
        super(message);
    }
}
