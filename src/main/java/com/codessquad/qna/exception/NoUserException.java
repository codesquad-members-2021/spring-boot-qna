package com.codessquad.qna.exception;

public class NoUserException extends RuntimeException {

    public NoUserException() {
        super("유저가 존재하지 않습니다.");
    }
}
