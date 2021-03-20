package com.codessquad.qna.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super(String.format("존재하지 않는 질문입니다; id: %d", id));
    }
}
