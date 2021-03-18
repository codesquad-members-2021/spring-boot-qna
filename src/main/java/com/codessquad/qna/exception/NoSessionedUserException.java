package com.codessquad.qna.exception;

public class NoSessionedUserException extends RuntimeException {

    public NoSessionedUserException() {
        super("해당 세션에 유저가 존재하지 않습니다.");
    }
}
