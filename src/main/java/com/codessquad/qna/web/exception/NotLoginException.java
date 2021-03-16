package com.codessquad.qna.web.exception;

public class NotLoginException extends RuntimeException {

    public NotLoginException(String msg){
        super(msg);
    }

    public NotLoginException(String msg, Throwable t){
        super(msg, t);
    }
}
