package com.codessquad.qna.exception;

public class QuestionNotFoundException extends RuntimeException {

    public static final String QUESTION_NOT_FOUND = "해당 질문을 찾을 수 없습니다. id : ";

    public QuestionNotFoundException(Long id) {
        super(QUESTION_NOT_FOUND + id);
    }

}
