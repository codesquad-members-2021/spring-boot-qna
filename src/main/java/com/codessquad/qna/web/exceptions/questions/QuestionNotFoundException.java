package com.codessquad.qna.web.exceptions.questions;

public class QuestionNotFoundException extends RuntimeException {
    private static final String ERROR_MESSAGE = "존재하지 않는 질문입니다!";

    public QuestionNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
