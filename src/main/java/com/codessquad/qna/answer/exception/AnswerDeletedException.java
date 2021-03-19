package com.codessquad.qna.answer.exception;

public class AnswerDeletedException extends RuntimeException {
    public AnswerDeletedException(Long id) {
        super(String.format("이미 삭제된 답변입니다. id: %d", id));
    }
}
