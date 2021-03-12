package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.exception.QuestionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionHandler {
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity handleQuestionNotFound(QuestionNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
