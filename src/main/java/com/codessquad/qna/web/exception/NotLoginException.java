package com.codessquad.qna.web.exception;

public class NotLoginException extends RuntimeException {
    public NotLoginException() {
        super("로그인을 해야 해당 기능을 사용할 수 있습니다");
    }
}
