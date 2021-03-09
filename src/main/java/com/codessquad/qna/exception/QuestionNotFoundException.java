package com.codessquad.qna.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super("해당 번호의 질문이 존재하지 않습니다.");
    }
}
