package com.codessquad.qna.exception;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException() {
        super("존재하지 않는 질문 입니다.");
    }
}
