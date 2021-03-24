package com.codessquad.qna.web.exception;

public class QuestionNotFoundException extends QnaException {
    public static String QUESTION_NOT_FOUND_MSG = "찾으시는 질문이 존재하지 않습니다.";

    public QuestionNotFoundException() {
        this(QUESTION_NOT_FOUND_MSG);
    }

    public QuestionNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
