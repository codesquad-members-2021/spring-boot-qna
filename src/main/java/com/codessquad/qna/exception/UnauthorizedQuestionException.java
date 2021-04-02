package com.codessquad.qna.exception;

public class UnauthorizedQuestionException extends RuntimeException {
    public static final String UNAUTHORIZED_FAILED_QUESTION = "질문글 (수정/삭제) 실패 : 권한이 부족하여 실패합니다 (당신은 질문글 작성자가 아닙니다), 혹은 다른사람의 답변이 달린 질문글은 삭제할 수 없습니다";
    public UnauthorizedQuestionException() {
        super(UNAUTHORIZED_FAILED_QUESTION);
    }
}
