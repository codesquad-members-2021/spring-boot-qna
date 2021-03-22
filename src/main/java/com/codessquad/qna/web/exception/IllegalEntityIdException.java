package com.codessquad.qna.web.exception;

public class IllegalEntityIdException extends RuntimeException {
    public IllegalEntityIdException() {
        super("id에 해당하는 item이 없습니다");
    }

    public IllegalEntityIdException(String message) {
        super(message);
    }
}
