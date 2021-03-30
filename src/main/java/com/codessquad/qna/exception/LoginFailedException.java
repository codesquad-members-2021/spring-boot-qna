package com.codessquad.qna.exception;

import static com.codessquad.qna.exception.ExceptionMessages.FAILED_LOGIN;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException() {
        super(FAILED_LOGIN);
    }
}
