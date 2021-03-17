package com.codessquad.qna.web.exception;

public class LoginException extends RuntimeException{

    public LoginException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
