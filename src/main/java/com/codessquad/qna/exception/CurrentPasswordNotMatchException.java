package com.codessquad.qna.exception;

public class CurrentPasswordNotMatchException extends RuntimeException{

    public CurrentPasswordNotMatchException() {
        super("기존 비밀번호가 일치하지 않습니다.");
    }

}
