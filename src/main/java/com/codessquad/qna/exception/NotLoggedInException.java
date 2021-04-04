package com.codessquad.qna.exception;

import com.codessquad.qna.domain.Result;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException() {
        super("Login Needed");
    }
}
