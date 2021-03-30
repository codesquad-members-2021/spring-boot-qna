package com.codessquad.qna.exception;

public class IllegalAccessOnUpdate extends RuntimeException{

    public IllegalAccessOnUpdate() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
