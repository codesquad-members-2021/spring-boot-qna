package com.codessquad.qna.utils;

public enum ErrorMessage {

    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다."),
    QUESTION_NOT_FOUND("해당 질문을 찾을 수 없습니다."),
    ANSWER_NOT_FOUND("해당 답변을 찾을 수 없습니다."),
    INVALID_USER("접근 권한이 없는 유저입니다."),
    DUPLICATED_ID("이미 사용중인 아이디입니다."),
    LOGIN_FAILED("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요."),
    WRONG_PASSWORD("기존 비밀번호가 일치하지 않습니다.");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
