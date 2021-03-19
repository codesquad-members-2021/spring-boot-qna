package com.codessquad.qna.exception;

public class NoSessionedUserException extends RuntimeException {

    public NoSessionedUserException() {
        super("로그인이 필요합니다.");
    }
}
