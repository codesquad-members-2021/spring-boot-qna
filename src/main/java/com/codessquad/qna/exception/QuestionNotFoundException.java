package com.codessquad.qna.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException() {
        super("Question Not Found");
    }
}
