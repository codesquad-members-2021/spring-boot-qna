package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {

    public IllegalUserAccessException() {
        super("접근 권한이 없는 유저입니다.");
    }

}
