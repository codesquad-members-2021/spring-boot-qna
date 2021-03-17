package com.codessquad.qna.exception;

public class UserNotFoundInSessionException extends RuntimeException {
    public UserNotFoundInSessionException() {
        super("Session에 사용자 정보가 남아있지 않습니다.");
    }
}
