package com.codessquad.qna.exception;

public class NoQuestionException extends RuntimeException {
    public NoQuestionException() {
        super("존재하지 않는 글입니다.");
    }
}
