package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.exception.AnswerDeletedException;
import com.codessquad.qna.answer.exception.AnswerNotFoundException;
import com.codessquad.qna.common.LoggerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AnswerExceptionHandler {
    private final LoggerRepository loggerRepository = new LoggerRepository(AnswerExceptionHandler.class);

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity handleAnswerNotFound(AnswerNotFoundException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AnswerDeletedException.class)
    public ResponseEntity handleAnswerDeleted(AnswerDeletedException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.badRequest().build();
    }
}
