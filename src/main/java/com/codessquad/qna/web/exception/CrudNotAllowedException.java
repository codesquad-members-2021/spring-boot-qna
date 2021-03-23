package com.codessquad.qna.web.exception;

public class CrudNotAllowedException extends RuntimeException {

    public CrudNotAllowedException(String msg) {
        super(msg);
    }

    public CrudNotAllowedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
