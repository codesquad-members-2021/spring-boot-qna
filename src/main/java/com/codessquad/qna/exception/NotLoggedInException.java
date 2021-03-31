package com.codessquad.qna.exception;

public class NotLoggedInException extends RuntimeException {
    public static final String NEED_LOGIN = "질문글 작성을 위해서는 로그인이 필요합니다";

    public NotLoggedInException() {
        super(NEED_LOGIN);
    }
}
