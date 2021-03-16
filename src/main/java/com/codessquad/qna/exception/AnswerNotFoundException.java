package com.codessquad.qna.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException() {
        super("해당하는 댓글이 존재하지 않습니다.");
    }
}
