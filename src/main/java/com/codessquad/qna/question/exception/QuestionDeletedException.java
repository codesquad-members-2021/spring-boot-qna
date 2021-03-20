package com.codessquad.qna.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuestionDeletedException extends RuntimeException {
    public QuestionDeletedException(Long id) {
        super(String.format("이미 삭제된 질문입니다. id: %d", id));
    }
}
