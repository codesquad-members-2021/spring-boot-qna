package com.codessquad.qna.exception;

public class NotFoundException extends RuntimeException {
    public static final String NOT_FOUNDED_USER = "요청한 데이터(User,Question,Answer) 데이터가 존재하지 않습니다";
    public NotFoundException() {
        super(NOT_FOUNDED_USER);
    }
}
