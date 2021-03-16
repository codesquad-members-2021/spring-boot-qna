package com.codessquad.qna.exception;

public class WriterOfAnswerListNotMatchException extends RuntimeException {

    private final Long questionId;

    public WriterOfAnswerListNotMatchException(Long questionId) {
        super("질문 작성자와 모든 답변의 작성자가 일치하지 않습니다.");
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

}
