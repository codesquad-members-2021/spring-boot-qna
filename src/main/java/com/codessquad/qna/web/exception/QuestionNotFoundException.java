package com.codessquad.qna.web.exception;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(String msg) {
        super(msg);
    }

    public QuestionNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
