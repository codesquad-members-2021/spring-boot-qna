package com.codessquad.qna.web.exception;

public class DuplicatedUserIdException extends RuntimeException{
    public DuplicatedUserIdException() {
        super("이미 존재하는 아이디입니다");
    }
}
