package com.codessquad.qna.question.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super(String.format("존재하지 않는 질문입니다; id: %d", id));
    }
}
