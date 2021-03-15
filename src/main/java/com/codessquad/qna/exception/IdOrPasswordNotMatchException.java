package com.codessquad.qna.exception;

public class IdOrPasswordNotMatchException extends  RuntimeException{

    public IdOrPasswordNotMatchException() {
        super("아이디나 비밀번호가 일치하지 않습니다.");
    }

}
