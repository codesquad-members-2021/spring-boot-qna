package com.codessquad.qna.exception;

public class AnswerNotFoundException extends RuntimeException{
    public AnswerNotFoundException() {
        super("Answer Not Found");
    }
}
