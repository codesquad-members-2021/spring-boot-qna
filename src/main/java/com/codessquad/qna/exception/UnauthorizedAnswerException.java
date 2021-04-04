package com.codessquad.qna.exception;

public class UnauthorizedAnswerException extends RuntimeException {
    public static final String UNAUTHORIZED_ANSWER_FAILED = "답변글을 수정/삭제 할 수 있는 권한이 부족하여 실패";

    public UnauthorizedAnswerException() {
        super(UNAUTHORIZED_ANSWER_FAILED);
    }
}
