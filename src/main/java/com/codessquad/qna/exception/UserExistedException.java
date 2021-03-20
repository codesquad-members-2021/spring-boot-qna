package com.codessquad.qna.exception;

public class UserExistedException extends RuntimeException{
    public UserExistedException() {
        super("이미 존재하는 사용자입니다.");
    }
}
