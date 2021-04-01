package com.codessquad.qna.web.exceptions.questions;

public class PageOutOfBoundaryException extends RuntimeException {
    private static final String ERROR_MESSAGE = "페이지가 유효범위를 벗어났습니다!";

    public PageOutOfBoundaryException() {
        super(ERROR_MESSAGE);
    }
}
