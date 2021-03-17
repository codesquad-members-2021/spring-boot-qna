package com.codessquad.qna.exception;

public class NoQuestionException extends RuntimeException {
    public NoQuestionException() {
        super("해당 질문이 존재하지 않습니다.");
    }
}
