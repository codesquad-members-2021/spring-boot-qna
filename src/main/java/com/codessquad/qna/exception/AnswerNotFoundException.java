package com.codessquad.qna.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException() {
        super("해당 Answer를 찾을 수 없습니다.");
    }
}
