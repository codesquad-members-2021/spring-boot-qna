package com.codessquad.qna.question.exception;

public class QuestionDeletedException extends RuntimeException {
    public QuestionDeletedException(Long id) {
        super(String.format("이미 삭제된 질문입니다. id: %d", id));
    }
}
