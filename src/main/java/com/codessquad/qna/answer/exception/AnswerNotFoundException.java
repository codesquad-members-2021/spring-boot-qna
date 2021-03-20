package com.codessquad.qna.answer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super(String.format("존재하지 않는 답변입니다; id: %d", id));
    }
}
