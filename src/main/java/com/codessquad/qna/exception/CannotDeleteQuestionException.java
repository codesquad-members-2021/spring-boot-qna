package com.codessquad.qna.exception;

public class CannotDeleteQuestionException extends RuntimeException {
    public CannotDeleteQuestionException() {
        super("Question 작성자가 아닌 사용자의 답변이 있어서 삭제할 수 없습니다.");
    }
}
