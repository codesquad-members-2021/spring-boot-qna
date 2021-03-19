package com.codessquad.qna.question.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuestionNotDeletableException extends RuntimeException {
    public QuestionNotDeletableException(String message) {
        super(message);
    }
}
