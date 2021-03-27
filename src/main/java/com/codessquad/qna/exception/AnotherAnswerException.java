package com.codessquad.qna.exception;

public class AnotherAnswerException extends RuntimeException {

    public AnotherAnswerException() {
        super("다른 사용자의 답변이 존재합니다.");
    }
}
