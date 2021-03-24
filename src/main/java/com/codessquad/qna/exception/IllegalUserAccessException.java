package com.codessquad.qna.exception;

public class IllegalUserAccessException extends RuntimeException {
    public IllegalUserAccessException() {
        super("User Can Only Access To Their Info/Posts");
    }
}
