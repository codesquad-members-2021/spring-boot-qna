package com.codessquad.qna.exception;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String entry) {
        super("해당 " + entry + "을(를) 찾을 수 없습니다.");
    }
}
