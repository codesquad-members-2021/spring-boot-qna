package com.codessquad.qna.exception;

public class CanNotFindPostException extends RuntimeException{

    public CanNotFindPostException() {
        super("해당 POST 를 찾을 수 없습니다.");
    }
}
