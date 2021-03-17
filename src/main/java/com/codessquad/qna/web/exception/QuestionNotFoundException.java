package com.codessquad.qna.web.exception;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
