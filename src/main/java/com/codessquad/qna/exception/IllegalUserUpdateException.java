package com.codessquad.qna.exception;

public class IllegalUserUpdateException extends RuntimeException {
    public IllegalUserUpdateException(Long id) {
        super(id + ": 해당 id 와는 일치하지 않습니다.");
    }
}
