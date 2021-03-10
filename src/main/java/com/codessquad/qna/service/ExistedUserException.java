package com.codessquad.qna.service;

public class ExistedUserException extends RuntimeException {

    public ExistedUserException() {
        super("이미 존재하는 회원입니다.");
    }
}
