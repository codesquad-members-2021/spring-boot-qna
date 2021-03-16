package com.codessquad.qna.web.exception;

public class AnswerNotFoundException extends RuntimeException{

    public AnswerNotFoundException(String msg) {
        super(msg);
    }

    public AnswerNotFoundException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
