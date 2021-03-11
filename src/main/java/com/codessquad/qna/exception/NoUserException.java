package com.codessquad.qna.exception;

public class NoUserException extends RuntimeException {
    public NoUserException() {
        super("존재하지 않는 User 입니다.");
    }
}
