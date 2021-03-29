package com.codessquad.qna.exception;

public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(){
        super("해당하는 정보가 이미 존재합니다.");
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
