package com.codessquad.qna.exception;

public class NotMatchException extends RuntimeException{

    public NotMatchException() {
        super("아이디 혹은 비밀번호가 일치하지 않습니다.");
    }
}
