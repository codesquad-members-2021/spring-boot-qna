package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.exception.QuestionDeletedException;
import com.codessquad.qna.question.exception.QuestionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(QuestionExceptionHandler.class);

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity handleQuestionNotFound(QuestionNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(QuestionDeletedException.class)
    public ResponseEntity handleQuestionDeleted(QuestionDeletedException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
