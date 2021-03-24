package com.codessquad.qna.exception;

public class JoinFailedException extends RuntimeException {

    public JoinFailedException() {
        super("회원가입에 실패했습니다.");
    }

    public JoinFailedException(String message) {
        super(message);
    }
}
