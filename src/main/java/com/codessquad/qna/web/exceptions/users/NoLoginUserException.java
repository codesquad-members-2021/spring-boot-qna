package com.codessquad.qna.web.exceptions.users;

public class NoLoginUserException extends RuntimeException {
    public NoLoginUserException(String message) {
        super(message);
    }
}
