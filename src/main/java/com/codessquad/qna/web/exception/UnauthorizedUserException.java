package com.codessquad.qna.web.exception;

public class UnauthorizedUserException extends RuntimeException {
    public static final String WRONG_PASSWORD = "비밀번호가 틀렸습니다.";
    public static final String UNAUTHORIZED_USER_TO_QUESTION = "글 작성자가 아닙니다.";
    public static final String UNAUTHORIZED_USER_TO_UPDATE = "본인의 회원정보만 수정할 수 있습니다.";

    public UnauthorizedUserException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
