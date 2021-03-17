package com.codessquad.qna.question.ui;

import com.codessquad.qna.common.LoggerRepository;
import com.codessquad.qna.question.exception.QuestionDeletedException;
import com.codessquad.qna.question.exception.QuestionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class QuestionExceptionHandler {
    private final LoggerRepository loggerRepository = new LoggerRepository(QuestionExceptionHandler.class);

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity handleQuestionNotFound(QuestionNotFoundException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(QuestionDeletedException.class)
    public ResponseEntity handleQuestionDeleted(QuestionDeletedException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.badRequest().build();
    }
}
