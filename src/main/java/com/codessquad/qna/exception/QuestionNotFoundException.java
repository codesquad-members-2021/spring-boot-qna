package com.codessquad.qna.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super("해당 질문을 찾을 수 없습니다");
    }
}
