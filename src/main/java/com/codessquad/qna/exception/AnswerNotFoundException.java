package com.codessquad.qna.exception;

public class AnswerNotFoundException extends RuntimeException {

    public static final String ANSWER_NOT_FOUND = "해당 답변을 찾을 수 없습니다. id : ";

    public AnswerNotFoundException(Long id) {
        super(ANSWER_NOT_FOUND + id);
    }

}
