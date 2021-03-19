package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.exception.AnswerDeletedException;
import com.codessquad.qna.answer.exception.AnswerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AnswerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(AnswerExceptionHandler.class);

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity handleAnswerNotFound(AnswerNotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AnswerDeletedException.class)
    public ResponseEntity handleAnswerDeleted(AnswerDeletedException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
