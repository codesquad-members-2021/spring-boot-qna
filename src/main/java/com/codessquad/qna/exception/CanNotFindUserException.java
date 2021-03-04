package com.codessquad.qna.exception;

public class CanNotFindUserException extends RuntimeException{

    public CanNotFindUserException() {
        super("해당 유저가 존재하지 않습니다.");
    }
}
