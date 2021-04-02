package com.codessquad.qna.web.exception;

public class AnswerNotFoundException extends QnaException {
    public static final String ANSWER_NOT_FOUND = "존재하지 않는 답변입니다.";

    public AnswerNotFoundException() {
        this(ANSWER_NOT_FOUND);
    }

    public AnswerNotFoundException(String message) {
        super(message);
    }
}
