package com.codessquad.qna.exception;

public class DuplicateUserIdFoundException extends RuntimeException {
    public DuplicateUserIdFoundException() {
        super("중복된 아이디가 존재합니다.");
    }
}
