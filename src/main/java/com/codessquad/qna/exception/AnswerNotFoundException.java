package com.codessquad.qna.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException() {
        super("해당 번호의 답변이 존재하지 않습니다.");
    }
}

