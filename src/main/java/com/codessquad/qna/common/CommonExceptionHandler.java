package com.codessquad.qna.common;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class CommonExceptionHandler {
    private final LoggerRepository loggerRepository = new LoggerRepository(CommonExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleIllegalArgsException(DataIntegrityViolationException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        loggerRepository.saveError(e);
        return ResponseEntity.notFound().build();
    }
}
