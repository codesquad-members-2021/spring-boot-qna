package com.codessquad.qna.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("값이 존재하지 않습니다.");
    }

}
