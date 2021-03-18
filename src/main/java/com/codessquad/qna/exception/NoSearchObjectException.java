package com.codessquad.qna.exception;

public class NoSearchObjectException extends RuntimeException {
    public NoSearchObjectException(String message) {
        super("해당" + message + "없음");
    }
}
