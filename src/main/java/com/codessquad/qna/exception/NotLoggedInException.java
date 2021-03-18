package com.codessquad.qna.exception;

public class NotLoggedInException extends RuntimeException {

    public NotLoggedInException() {
        super("Not Logged In");
    }
}
