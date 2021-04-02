package com.codessquad.qna.exception;

public class IndexOutOfPageException extends RuntimeException {
    public IndexOutOfPageException() {
        super("잘못된 페이지 번호입니다.");
    }

    public IndexOutOfPageException(String message) {
        super(message);
    }
}
